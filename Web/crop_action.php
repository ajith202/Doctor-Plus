<?php
@session_start();
$image = $_POST['image'];
$original_width = getimagesize($image);
$original_width = $original_width[0];
$original_height = getimagesize($image);
$original_height = $original_height[1];
$new_width = $_POST['current_width'];
$new_height = intval(($original_height / $original_width) * $new_width);
$x1 = $_POST['x1'];
$x2 = $_POST['x2'];
$y1 = $_POST['y1'];
$y2 = $_POST['y2'];
$crop_start_x = $x1;
$crop_start_y = $y1;
$crop_width = $x2 - $x1;
$crop_height = $y2 - $y1;
$original_src_image = imagecreatefromjpeg($image);
$src_image = imagecreatetruecolor($new_width, $new_height);
imagecopyresampled($src_image, $original_src_image, 0, 0, 0, 0, $new_width, $new_height, $original_width, $original_height);
$dist_image = imagecreatetruecolor($crop_width, $crop_height);
imagecopyresampled($dist_image, $src_image, 0, 0, $crop_start_x, $crop_start_y, $crop_width, $crop_height, $crop_width, $crop_height);
imagejpeg($dist_image, $image);
unset($_SESSION['crop']);
echo "done";
?>

