<!DOCTYPE html>
<?php
@session_start();
if(empty($_SESSION['crop']) || $_SESSION['crop'] != "on"){
    header("Location:index.php");
}
$image = urldecode($_GET['image']);
$to = urldecode($_GET['to']);
$aspect = urldecode($_GET['aspect']);
$aspect_splt = explode(".", $aspect);
$aspect_width = $aspect_splt[0];
$aspect_height = $aspect_splt[1];
?>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Admin - Holidayspecial Tours</title>
        <link rel='shortcut icon' type='image/x-icon' href='../images/favicon.png' />
        <link href='http://fonts.googleapis.com/css?family=Roboto:400' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="css/bootstrap.css" />
        <link rel="stylesheet" href="css/style.css" rel="stylesheet">
        <link href="css/imgareaselect-animated.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.imgareaselect.js" type="text/javascript"></script>
        <script src="js/jquery.imgareaselect.pack.js" type="text/javascript"></script>
        <script>
            $(document).ready(function () {
                $('#crop_mdl').modal('show');
                var x1, x2, y1, y2;
                $('.image_to_crop').imgAreaSelect({
                    x1: 120, y1: 90, x2: 280, y2: 210,
                    handles: true,
                    aspectRatio: '<?php echo $aspect_width; ?> : <?php echo $aspect_height; ?>',
                                onSelectEnd: function (img, selection) {
                                    x1 = selection.x1;
                                    x2 = selection.x2;
                                    y1 = selection.y1;
                                    y2 = selection.y2;
                                }
                            });
                            $('.crop-btn').click(function () {
                                var crop_image = $('.image_to_crop').attr('src');
                                var display_width = $('.image_to_crop').width();
                                $.post('crop_action.php', {
                                    'image': crop_image,
                                    'current_width': display_width,
                                    'x1': x1,
                                    'x2': x2,
                                    'y1': y1,
                                    'y2': y2
                                }, function (data) {
                                    window.location.assign("<?php echo $to; ?>.php");
                                });

                            });

                        });
        </script>
        <style>
            body{
                background: url(<?php echo $image; ?>);
                background-size: cover;
            }
            .crop-btn{
                width: 100%;
            }
        </style>
        <!--[if lt IE 9]>
     <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
     <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
         $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
   <![endif]-->
    </head>

    <body>


        <div class="modal fade" id="crop_mdl"  data-backdrop="static" >
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title">Crop Image</h4>
                    </div>
                    <div class="modal-body" align="center">
                        <img class="img-responsive image_to_crop" src="<?php echo $image; ?>" width="100%">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary crop-btn">Crop Image</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-------------------------------------->
    </body>
</html>
