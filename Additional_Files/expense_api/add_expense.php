<?php
header('Content-Type: application/json; charset=utf-8');
require 'db_connect.php';

$uid = $_POST['user_id'];
$title = $_POST['title'];
$amount = $_POST['amount'];
$date = $_POST['date'];
$type = $_POST['type'];

$stmt = $conn->prepare("INSERT INTO expenses (user_id, title, amount, date, type) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param("issss", $uid, $title, $amount, $date, $type);

if ($stmt->execute()) {
    echo json_encode(array("status" => "success", "message" => "Saved"));
} else {
    echo json_encode(array("status" => "error", "message" => "Failed"));
}
?>