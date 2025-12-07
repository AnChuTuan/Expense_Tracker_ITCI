<?php
// Bật chế độ báo lỗi chi tiết của MySQL
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

$host = "localhost";
$user = "root";
$pass = ""; // Mặc định Wamp là rỗng. Nếu bạn đã đổi pass thì điền vào đây.
$db_name = "expense_tracker_db";

try {
    $conn = new mysqli($host, $user, $pass, $db_name);
    $conn->set_charset("utf8mb4"); // Hỗ trợ tiếng Việt
} catch (Exception $e) {
    // Nếu lỗi, in ra JSON để App Android và Trình duyệt đều đọc được
    die(json_encode(array(
        "status" => "error",
        "message" => "Lỗi kết nối DB: " . $e->getMessage()
    )));
}
?>