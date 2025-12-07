<?php
error_reporting(0); // Tắt lỗi rác để JSON không bị hỏng
header('Content-Type: application/json; charset=utf-8');
require 'db_connect.php';

$uid = $_GET['user_id'];

// Lấy danh sách, sắp xếp ngày đến hạn gần nhất lên đầu
$sql = "SELECT * FROM bills WHERE user_id='$uid' ORDER BY due_date ASC";
$result = $conn->query($sql);

$bills = array();
while($row = $result->fetch_assoc()) {
    $bills[] = $row;
}
echo json_encode(["status" => "success", "data" => $bills]);
?>