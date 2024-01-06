# NHỮNG LƯU Ý KHI CHẠY PROJECT

# 1. Build project
- Khi build project, nếu project không có Run/Debug Configuration thì ta nhấn vào Add Configuration -> Add New configuration (dấu +) -> Android App -> ở mục Module, chọn EnglishVocabQuiz.app.main -> apply -> ok
- nếu project bị lỗi không tìm thấy Sdk mà android studio không tự động đồng bộ Sdk thì ta vào Gradle Script -> local.propeties -> tại dòng "sdk.dir=" ta thêm đường dẫn Sdk của máy tính vào.

# 1. Đăng nhập
- Hiện tại đang có các tài khoản sau đây đã được tạo và có sẵn dữ liệu:

	Tài khoản 1: nguyentienthang100120013@gmail.com – Mật khẩu: 123456789

	Tài khoản 2: lehaitien422003dev@gmail.com – Mật khẩu: 123123

	Tài khoản 3: vutran.ac98@gmail.com – Mật khẩu: 987654321

	Tài khoản 4: thang@gmail.com – Mật khẩu: 123456789

- Ngoài các tài khoản trên ta cũng có thể đăng kí tài khoản mới, nếu email được đăng kí là email chính thống thì có thể thực hiện chức năng quên mật khẩu.
- Quên mật khẩu: khi ấn vào nút quên mật khẩu, một email sẽ được gửi đến mail của tài khoản, khi đó chỉ cần nhấp vào liên kết gửi trong mail, nhập mật khẩu mới vào liên kết đó là có thể đổi được mật khẩu.

# 2. Test các chức năng
- Các topic trong trang chủ và trong mục thư mục đều có thể thực hiện các chức năng cơ bản của một topic: học bằng flashcard, kiểm tra trắc nghiệm, kiểm tra điền từ.
- Khi một topic được tạo thì có thể không có từ vựng nào tuy nhiên các chức năng của topic đó sẽ không hoạt động được, đặc biệt chức năng kiểm tra trắc nghiệm yêu cầu topic phải có tối thiểu 4 từ vựng mới có thể thực hiện.
- Trên trang chủ có hai mục là đề xuất và chủ đề của tôi. Mục đề xuất hiển thị các topic không phải do tài khoản đang đăng nhập tạo ra và đang ở chế độ public, còn mục chủ đề của tôi hiển thị các topic do tài khoản đang đăng nhập tạo.  
