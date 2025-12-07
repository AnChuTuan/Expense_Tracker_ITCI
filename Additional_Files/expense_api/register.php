<?php
// --- KHỐI DEBUG (Sẽ xóa khi chạy ngon) ---
// Ép buộc hiển thị mọi lỗi ra màn hình để biết tại sao bị 500
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
// -----------------------------------------

header('Content-Type: application/json; charset=utf-8');

// 1. Kiểm tra xem file db_connect.php có tồn tại không
if (!file_exists('db_connect.php')) {
    die(json_encode(array("status" => "error", "message" => "File db_connect.php khong ton tai trong cung thu muc!")));
}

// 2. Gọi file kết nối
require 'db_connect.php';

// 3. Kiểm tra biến $conn từ db_connect.php có hoạt động không
if (!isset($conn)) {
    die(json_encode(array("status" => "error", "message" => "Bien \$conn chua duoc khoi tao trong db_connect.php")));
}

// 4. Kiểm tra dữ liệu đầu vào
if (!isset($_POST['email']) || !isset($_POST['password']) || !isset($_POST['name'])) {
    // Nếu chạy trên trình duyệt mà không gửi data POST, nó sẽ báo lỗi này -> Là bình thường
    echo json_encode(array("status" => "error", "message" => "Thieu du lieu: email, password hoac name"));
    exit();
}

$email = $_POST['email'];
$pass = $_POST['password']; // Trong thực tế nên dùng password_hash($pass, PASSWORD_DEFAULT)
$name = $_POST['name'];

// 5. Thực hiện Query
// Dùng prepare statement để tránh lỗi cú pháp SQL khi có ký tự đặc biệt
$stmt = $conn->prepare("SELECT id FROM users WHERE email = ?");
if (!$stmt) {
    die(json_encode(array("status" => "error", "message" => "Lỗi SQL Prepare: " . $conn->error)));
}
$stmt->bind_param("s", $email);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    echo json_encode(array("status" => "error", "message" => "Email already exists"));
} else {
    $stmt_insert = $conn->prepare("INSERT INTO users (email, password, full_name) VALUES (?, ?, ?)");
    if (!$stmt_insert) {
        die(json_encode(array("status" => "error", "message" => "Lỗi SQL Insert: " . $conn->error)));
    }
    $stmt_insert->bind_param("sss", $email, $pass, $name);
    
    if ($stmt_insert->execute()) {
        echo json_encode(array("status" => "success", "message" => "User registered"));
    } else {
        echo json_encode(array("status" => "error", "message" => "Lỗi thực thi: " . $stmt_insert->error));
    }
}
?>