<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<<<<<<< HEAD
        <link href="table.css" rel="stylesheet"> 
</head>
=======
        <link href="table.css" rel="stylesheet">
 </head>
>>>>>>> e2bc8e494ac9874e174c3b2bd45e1761775db5e5
<body>
<h3>summary结果如下：</h3>

<div id="bodyframe" >
 <iframe src="
 <?php
  $vcompile=  $_GET["summary"];
  $vcompile=str_replace("%","%25",$vcompile);
  $vcompile="summary/".$vcompile;
  echo $vcompile
?>

<<<<<<< HEAD
"frameborder="0" ></iframe>
<!--" marginheight="0" marginwidth="0" frameborder="0" width=100% height=100% id="iframepage" name="iframepage" onLoad="iFrameHeight()" ></iframe> -->

<h4>表格如下：</h4>
<?php
  $vcompile=  $_GET["summary"];
  $vcompile=str_replace("%","%25",$vcompile);
  $com=substr($vcompile,9);
  system("python getdata.py ".$com);
?>

<div id="restable"></div>

<script src="jquery-1.11.3.min.js"></script>
<script src="jquery.columns.min.js"></script>
<script>
	$.ajax({
                url:'data.json',
                dataType: 'json',
                success: function(json) {
                     restable = $('#restable').columns({
                        data:json
                    });
                }
        });
</script>
=======
"
 marginheight="0" marginwidth="0" frameborder="0" width=100% height=100% id="iframepage" name="iframepage" onLoad="iFrameHeight()" ></iframe>
<?php
  $vcompile=  $_GET["summary"];
  $vcompile=str_replace("%","%25",$vcompile);
  $com=substr($vcompile,9);
  system("python getdata.py ".$com);
?>

<h4>表格如下：</h4>
<div id="restable"></div>

<script src="jquery-1.11.3.min.js"></script>
<script src="jquery.columns.min.js"></script>
<script>
	$.ajax({
                url:'data.json',
                dataType: 'json',
                success: function(json) {
                     restable = $('#restable').columns({
                        data:json
                    });
                }
        });
</script>

>>>>>>> e2bc8e494ac9874e174c3b2bd45e1761775db5e5
</div>
</body>
</html>
