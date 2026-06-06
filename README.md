# NapThe

Plugin nạp thẻ cho Minecraft server (Spigot/Paper).

## Tính năng
- Hỗ trợ nạp thẻ qua API (TheSieuToc).
- Giao diện GUI chọn loại thẻ (/napthe).
- Tùy chỉnh phần thưởng (lệnh console) và tin nhắn qua config.yml.
- Quản lý thẻ nạp và lưu trữ vào SQLite.
- **Hỗ trợ Java 8 trở lên.**
- **Tương thích với các phiên bản Minecraft (1.12+ hỗ trợ tốt nhất).**

## Lệnh và Quyền hạn
- `/napthe`: Mở giao diện chọn loại thẻ.
- `/napthe <loại> <mệnh giá> <seri> <mã thẻ>`: Gửi thẻ trực tiếp.
- Quyền: `napthe.use` (mặc định cho tất cả mọi người).

## Nạp Thẻ Qua Website
Plugin tích hợp một Web Server nhỏ để nhận **Callback** từ các bên API (như TheSieuToc).
- Mặc định chạy tại port `8080`.
- Website nạp thẻ: `https://web.yankaree.indevs.in/`
- Đường dẫn callback: `http://<IP-Của-Bạn>:8080/callback`.

## Yêu cầu
- Java 8 trở lên.
- Spigot/Paper/CraftBukkit.
- Vault (tùy chọn).

## Cài đặt
1. Tải file `.jar` từ phần [Releases](https://github.com/AnNgTv/NapThe/releases).
2. Bỏ vào thư mục `plugins` của server.
3. Khởi động lại server và cấu hình trong `plugins/NapThe/config.yml`.
