<?php
error_reporting(0);
header('Content-Type: application/json; charset=utf-8');
require 'db_connect.php';

$id = $_POST['id'];
$status = $_POST['status'];

$stmt = $conn->prepare("UPDATE bills SET status = ? WHERE id = ?");
$stmt->bind_param("si", $status, $id);

if ($stmt->execute()) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
?>