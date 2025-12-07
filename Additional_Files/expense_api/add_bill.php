<?php
error_reporting(0);
header('Content-Type: application/json; charset=utf-8');
require 'db_connect.php';

$uid = $_POST['user_id'];
$title = $_POST['title'];
$amount = $_POST['amount'];
$date = $_POST['due_date'];
$status = 'UNPAID'; // Mặc định chưa trả

$stmt = $conn->prepare("INSERT INTO bills (user_id, title, amount, due_date, status) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param("isdss", $uid, $title, $amount, $date, $status);

if ($stmt->execute()) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
?>