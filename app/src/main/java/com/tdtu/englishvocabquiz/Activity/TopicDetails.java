package com.tdtu.englishvocabquiz.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.tdtu.englishvocabquiz.Adapter.VocabAdapter;
import com.tdtu.englishvocabquiz.Dialog.ConfirmDeleteDialog;
import com.tdtu.englishvocabquiz.Listener.Folder.OnDeleteFolderListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnAddTopicListener;
import com.tdtu.englishvocabquiz.Listener.Topic.OnDeleteTopicListener;
import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Listener.User.OnGetUserListener;
import com.tdtu.englishvocabquiz.Model.UserModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;
import com.tdtu.englishvocabquiz.Service.UserDatabaseService;
import com.tdtu.englishvocabquiz.databinding.ActivityTopicDetailsBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class TopicDetails extends AppCompatActivity {

    private ActivityTopicDetailsBinding binding;
    private UserDatabaseService userDatabaseService = new UserDatabaseService(getApplication());
    private TopicDatabaseService topicDatabaseService = new TopicDatabaseService(this);
    private FirebaseFirestore firestore;
    private CollectionReference collectionReference;
    private ArrayList<VocabularyModel> vocabList;
    private VocabAdapter vocabAdapter;
    private CSVWriter writer = null;
    private static final int PICK_CSV_FILE = 1;
//    String featureType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String TopicName = intent.getStringExtra("TopicName");
        String NumberOfVocab = String.valueOf(intent.getIntExtra("NumberOfVocab",1));
        String IdAuthor = intent.getStringExtra("IdAuthor");
        String IdTopic = intent.getStringExtra("IdTopic");
//        String AuthorName = intent.getStringExtra("AuthorName");

        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("topics");
        topicDatabaseService = new TopicDatabaseService(getApplication());
        userDatabaseService.getUserById(IdAuthor,new OnGetUserListener() {
            @Override
            public void onGetReady(UserModel userModel) {
                String AuthorName = userModel.getName();
                binding.tvNameAuthor.setText(AuthorName);            }
        });
        binding.tvtopic.setText(TopicName);
        binding.tvNumWord.setText(NumberOfVocab + " thuật ngữ");

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TopicDetails.this, RankingActivity.class);
                intent1.putExtra("idTopic",IdTopic);
                startActivity(intent1);
            }
        });
        String folder = intent.getStringExtra("folder");
        if(folder == null){
            if(FirebaseAuth.getInstance().getUid().equals(IdAuthor)){
                binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog(TopicDetails.this, IdTopic, new OnDeleteTopicListener() {
                            @Override
                            public void OnDeleteSuccess() {
                                finish();
                            }

                            @Override
                            public void OnDeleteFailure() {

                            }
                        });
                        dialog.showCreateDialogTopic();
                    }
                });
            }else {
                binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Chỉ người tạo mới có thể xóa",Toast.LENGTH_SHORT).show();
                    }
                });

                }
        }else {
            binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idFolder = intent.getStringExtra("folder");
                    ConfirmDeleteDialog dialog = new ConfirmDeleteDialog(TopicDetails.this, idFolder, new OnDeleteFolderListener() {
                        @Override
                        public void OnDeleteSuccess() {
                            finish();
                        }

                        @Override
                        public void OnDeleteFailure() {

                        }
                    },IdTopic);
                    dialog.showCreateDialogFolderDeleteTopic();
                }
            });
        }

        if(FirebaseAuth.getInstance().getUid().equals(IdAuthor)){
            binding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(getApplicationContext(), UpdateTopic.class);
                    intent1.putExtra("IdTopic",IdTopic);
                    startActivity(intent1);
                }
            });
        }else {
            binding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Chỉ người tạo mới có thể chỉnh sửa",Toast.LENGTH_SHORT).show();

                }
            });
        }
        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TopicDetails.this,MoveTopicTopFolder.class);
                intent1.putExtra("IdTopic",IdTopic);
                String id = FirebaseAuth.getInstance().getUid().toString();
                intent1.putExtra("IdAuthor",id);
                startActivity(intent1);
            }
        });
        binding.btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), AddWord.class);
                intent1.putExtra("IdTopic" ,IdTopic);
                intent1.putExtra("TopicName",TopicName);
                startActivity(intent1);
            }
        });

/////////////////////// go to study feature

        binding.btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
                    @Override
                    public void onListReady(ArrayList<VocabularyModel> vocabList) {
                        if(vocabList.size()<1){
                            Toast.makeText(getApplicationContext(), "Yêu cầu topic có tối thiểu 1 từ để có thể thực hiện chức năng này", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent1 = new Intent(getApplicationContext(), OptionActivity.class);
                            intent1.putExtra("IdTopic" ,IdTopic);
                            intent1.putExtra("featureType" ,"card");
                            startActivity(intent1);
                        }
                    }
                });

            }
        });
        binding.btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
                    @Override
                    public void onListReady(ArrayList<VocabularyModel> vocabList) {
                        if(vocabList.size()<4){
                            Toast.makeText(getApplicationContext(), "Yêu cầu topic có tối thiểu 4 từ để có thể thực hiện chức năng này", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent1 = new Intent(getApplicationContext(), OptionActivity.class);
                            intent1.putExtra("IdTopic" ,IdTopic);
                            intent1.putExtra("featureType" ,"choice");
                            startActivity(intent1);
                        }
                    }
                });

            }
        });

        binding.btnTypeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
                    @Override
                    public void onListReady(ArrayList<VocabularyModel> vocabList) {
                        if(vocabList.size()<1){
                            Toast.makeText(getApplicationContext(), "Yêu cầu topic có tối thiểu 1 từ để có thể thực hiện chức năng này", Toast.LENGTH_LONG).show();
                        }
                        else {

                        }
                    }
                });
                Intent intent1 = new Intent(getApplicationContext(), OptionActivity.class);
                intent1.putExtra("IdTopic" ,IdTopic);
                intent1.putExtra("featureType" ,"typeWord");
                startActivity(intent1);
            }
        });


        vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
            @Override
            public void onListReady(ArrayList<VocabularyModel> vocabList) {
                vocabAdapter = new VocabAdapter(getApplicationContext(), vocabList);
                binding.rclWord.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.rclWord.setAdapter(vocabAdapter);

            }
        });

        collectionReference.document(IdTopic)
                .collection("words")
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                // Kiểm tra nếu querySnapshot không null và có chứa document mới
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    //xoa list cu
                    vocabList.clear();
                    //cap nhat list moi
                    vocabList = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
                        @Override
                        public void onListReady(ArrayList<VocabularyModel> vocabList) {

                            if (vocabList != null) {
                                vocabAdapter = new VocabAdapter(getApplicationContext(), vocabList);
                                binding.rclWord.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.rclWord.setAdapter(vocabAdapter);
                                vocabAdapter.setOnItemClickListener(new VocabAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent = new Intent(getApplicationContext(), UpdateWord.class);
                                        intent.putExtra("IdTopic", vocabList.get(position).getIdTopic());
                                        intent.putExtra("IdWord",vocabList.get(position).getId());
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                }else {
                    vocabList.clear();
                    binding.rclWord.setAdapter(vocabAdapter);

                }
            }
        });

//        String csvFileName = "MyCsvFile.csv";
        String csvFileName = TopicName + ".csv";
        File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), csvFileName);
        binding.btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    writer = new CSVWriter(new FileWriter(csvFile));


                    ArrayList<VocabularyModel> list = new ArrayList<>();
                    list = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
                        @Override
                        public void onListReady(ArrayList<VocabularyModel> vocabList) {
                            List<String[]> data = new ArrayList<>();
                            for (VocabularyModel vocab : vocabList) {
                                // Đưa thông tin từ mỗi đối tượng Student vào một mảng String[]
                                String[] rowData = new String[]{
                                        vocab.getEnglish(),
                                        vocab.getVietnamese(),
                                };
                                data.add(rowData); // Thêm mảng String[] vào danh sách dữ liệu
                            }
                            try {
                                writer.writeAll(data); // Ghi dữ liệu vào file CSV
                                writer.close(); // Đóng writer sau khi ghi xong
                                Log.e("TAG", "Download: ");
                                Toast.makeText(getApplicationContext(),"Đã tải về " + csvFileName,Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });
//                    callRead();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Trong phương thức của bạn, ví dụ trong một phương thức onClick cho một nút:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*"); // Chỉ chọn file có định dạng CSV
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, PICK_CSV_FILE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CSV_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData(); // Nhận URI của file đã chọn
            String path = uri.getPath(); // Lấy đường dẫn từ URI

            // Xử lý đường dẫn file ở đây, ví dụ:
//            readCSVFile(path);
            readCSVFromUri(uri);
        }else {
            if(resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }
//
//    private void readCSVFile(String filePath) {
//        // Đọc file CSV từ đường dẫn filePath
//        // Tiếp tục xử lý dữ liệu từ file CSV ở đây
//        String csvFile = filePath; // Đường dẫn tới file CSV của bạn
//        Intent intent = getIntent();
//        String IdTopic = intent.getStringExtra("IdTopic");
//        topicDatabaseService = new TopicDatabaseService(getApplicationContext());
//
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Xử lý từng dòng của file CSV ở đây
//                String[] data = line.split(","); // Tách các giá trị bằng dấu phẩy (có thể thay đổi tùy theo dấu ngăn cách trong file CSV)
//
////                // Ví dụ: in ra từng giá trị trong mỗi dòng
////                for (String value : data) {
////                    System.out.print(value + " | ");
////                }
////                System.out.println(); // Xuống dòng cho dòng tiếp theo
//                VocabularyModel vocabularyModel = new VocabularyModel();
//                vocabularyModel.setEnglish(data[0]);
//                vocabularyModel.setVietnamese(data[1]);
//                vocabularyModel.setIdTopic(IdTopic);
//                topicDatabaseService.addWordToTopic(IdTopic, vocabularyModel, new OnAddTopicListener() {
//                    @Override
//                    public void OnAddSuccess() {
//                        Toast.makeText(TopicDetails.this, "Import thanh cong", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void OnAddFailure() {
//
//                    }
//                });
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    public void readCSVFromUri(Uri uri) {
        Intent intent = getIntent();
        String IdTopic = intent.getStringExtra("IdTopic");
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);

            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    topicDatabaseService = new TopicDatabaseService(getApplicationContext());
                    // Xử lý từng dòng của file CSV ở đây
                    String[] values = line.split(","); // Tách giá trị dựa trên dấu phẩy (hoặc ký tự ngăn cách khác)
                    // Xử lý các giá trị trong mỗi dòng
                    VocabularyModel vocabularyModel = new VocabularyModel();
                    vocabularyModel.setEnglish(values[0].replaceAll("\"", ""));
                    vocabularyModel.setVietnamese(values[1].replaceAll("\"", ""));
                    vocabularyModel.setIdTopic(IdTopic);
                    topicDatabaseService.addWordToTopic(IdTopic, vocabularyModel, new OnAddTopicListener() {
                        @Override
                        public void OnAddSuccess() {
                            Toast.makeText(TopicDetails.this, "Import thanh cong", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnAddFailure() {

                        }
                    });

                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



