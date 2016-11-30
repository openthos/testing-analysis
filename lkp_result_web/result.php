<!-- 对button 进程form 和action 处理，使用get方法，可以使得URL中出现参数-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Android LKP结果展示</title>

   <script type="text/javascript" src="jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="handlebars-v3.0.3.js"></script>


    <script type="text/javascript" src="test.js"></script>



<?php
 //system('./b.sh >./test.js');
// $test = "ls /tmp";
 $test = "./b.sh >./test.js";
$last = system($test);
//print "last: $last\n";
?>



<script language="JavaScript">


<?php
function my_scandir($dir)
{
    $files=array();
    if(is_dir($dir))
    {
        if($handle=opendir($dir))
        {
            while(($file=readdir($handle))!==false)
            {
                if($file!="." && $file!="..")
                {   
                    if(is_dir($dir."/".$file))
                    {   
                        $files[$file]=my_scandir($dir."/".$file);
                    }
                }
            }
            closedir($handle);
            return $files;
        }
    }
}


function my_scandir2($dir)
{
    $files=array();
    if(is_dir($dir))
    {
        if($handle=opendir($dir))
        {
            while(($file=readdir($handle))!==false)
            {
                if($file!="." && $file!="..")
                {
                    if(!is_dir($dir."/".$file))
                    {
                        $files[$file]=$file;
                    }
                }
            }
            closedir($handle);
            return $files;
        }
    }
}


function my_scandir3($dir)
{
    $files=array();
    if(is_dir($dir))
    {
        if($handle=opendir($dir))
        {
            while(($file=readdir($handle))!==false)
            {
                if($file!="." && $file!="..")
                {
                    if(is_dir($dir."/".$file))
                    {
                        $test =  "./lkp_web_oto/getfilelist.py -f ".$file;
                        $last = system($test);
                        $test =  "./lkp_web_oto/insertdata.py -f ".$file;
                        $last = system($test);
                        $files[$file]=$file;
                    }
                }
            }
            closedir($handle);
            return $files;
        }
    }
}





function  my_sort(&$a)
{
	if(count($a)>=1)
	{
	krsort($a);
	foreach($a as $x=>&$x_value)
	{
	     my_sort($x_value);
	}
	}
}


$aaa=my_scandir("/mnt/freenas/result");

my_sort($aaa);



echo "    var Place_dict = ";
echo json_encode($aaa,JSON_FORCE_OBJECT|JSON_UNESCAPED_SLASHES);
echo ";";


$jcompile=my_scandir2("/mnt/freenas/compile");
krsort($jcompile);
echo "    var compile_dict = ";
echo json_encode($jcompile,JSON_FORCE_OBJECT|JSON_UNESCAPED_SLASHES);
echo ";";


$jcsv=my_scandir3("/mnt/freenas/result");
krsort($jcsv);
echo "    var csv_dict = ";
echo json_encode($jcsv,JSON_FORCE_OBJECT|JSON_UNESCAPED_SLASHES);
echo ";";





$jsummary=my_scandir2("/mnt/freenas/summary");
krsort($jsummary);
echo "    var summary_dict = ";
echo json_encode($jsummary,JSON_FORCE_OBJECT|JSON_UNESCAPED_SLASHES);
echo ";";

?>	


function all_init()
{
 csv_changeselect("init");
 changeselect("init");
 compile_changeselect("init");
 summary_changeselect("init");
}


function csv_changeselect(aid)
{



 if(aid=="init")
  {
      for(acsv in csv_dict){

        var text = "<option>"+acsv+"</option>";
                $("#benchmarks").append(text);
        console.log(text);
      }
  }


}




function compile_changeselect(aid)
{



 if(aid=="init") 
  {
      for(acompile in compile_dict){ 

        var text = "<option>"+acompile+"</option>";
                $("#compile").append(text);
        console.log(text); 
      }
  }


}




function summary_changeselect(aid)
{



 if(aid=="init") 
  {
      for(asummary in summary_dict){ 

        var text = "<option>"+asummary+"</option>";
                $("#summary").append(text);
        console.log(text); 
      }
  }


}












//可以递归调用吗？	changeselect(aid)
//递归调用达到了联动的目的
 
function changeselect(aid)
{
  var A=document.getElementById("testcase")
  var B=document.getElementById("test_config")
  var C=document.getElementById("tbox_group" )
  var D=document.getElementById("rootfs")
  var E=document.getElementById("kconfig")
  var F=document.getElementById("compiler" )
  var G=document.getElementById("commit")
  var H=document.getElementById("run")  
  
 if(aid=="init") 
  {
      for(city in Place_dict){ 

        var text = "<option>"+city+"</option>";
		$("#testcase").append(text);
        console.log(text); 
      }
  changeselect("testcase");	  
}
  
  
if (aid=="testcase")
 {
    $("#test_config").empty();			
	 var n_dict = Place_dict[A.value];
	 
      for(city in n_dict){  
        
        var text = "<option>"+city+"</option>";
		$("#test_config").append(text);
        console.log(text); //用来观察生成的text数据
      }	 
	  changeselect("test_config")
 }
 if (aid=="test_config")
 {
    $("#tbox_group").empty();
	 var n_dict = Place_dict[A.value][B.value];
	 
      for(city in n_dict){  
       
        var text = "<option>"+city+"</option>";
		$("#tbox_group").append(text);
        console.log(text); //用来观察生成的text数据
      }	 
      changeselect("tbox_group")
 }
  if (aid=="tbox_group")
 {
    $("#rootfs").empty();
	 var n_dict = Place_dict[A.value][B.value][C.value];
	 
      for(city in n_dict){  
       
        var text = "<option>"+city+"</option>";
		$("#rootfs").append(text);
        console.log(text); //用来观察生成的text数据
      }	 
      changeselect("rootfs")
 }
 
 
   if (aid=="rootfs")
 {
    $("#kconfig").empty();
	 var n_dict = Place_dict[A.value][B.value][C.value][D.value];
	 
      for(city in n_dict){  
       
        var text = "<option>"+city+"</option>";
		$("#kconfig").append(text);
        console.log(text); //用来观察生成的text数据
      }	 
      changeselect("kconfig")
 }
 
 
   if (aid=="kconfig")
 {
    $("#compiler").empty();
	 var n_dict = Place_dict[A.value][B.value][C.value][D.value][E.value];
	 
      for(city in n_dict){  
       
        var text = "<option>"+city+"</option>";
		$("#compiler").append(text);
        console.log(text); //用来观察生成的text数据
      }	 
      changeselect("compiler")
 }
 
   if (aid=="compiler")
 {
    $("#commit").empty();
	 var n_dict = Place_dict[A.value][B.value][C.value][D.value][E.value][F.value];
	 
      for(city in n_dict){  
       
        var text = "<option>"+city+"</option>";
		$("#commit").append(text);
        console.log(text); //用来观察生成的text数据
      }	 
      changeselect("commit")
 }
 
   if (aid=="commit")
 {
    $("#run").empty();
	 var n_dict = Place_dict[A.value][B.value][C.value][D.value][E.value][F.value][G.value];
	 
      for(city in n_dict){  
       
        var text = "<option>"+city+"</option>";
		$("#run").append(text);
        console.log(text); //用来观察生成的text数据
      }	 
      changeselect("run")
 }
 
}

function button_click(aid)
{
  var A=document.getElementById("testcase")
  var B=document.getElementById("test_config")
  var C=document.getElementById("tbox_group" )
  var D=document.getElementById("rootfs")
  var E=document.getElementById("kconfig")
  var F=document.getElementById("compiler" )
  var G=document.getElementById("commit")
  var H=document.getElementById("run") 

  var url=A.value+'/'+B.value+'/'+C.value+'/'+D.value+'/'+E.value+'/'+F.value+'/'+G.value+'/'+H.value+'/';
  url=url.replace(/%/g,"%25")
/*
  url="http://192.168.2.128/result/"+url
*/
  url="result/"+url
  console.log(url);
   
  document.getElementById("iframepage").src=url;
  bodyframe.style.visibility='visible';

}



function compile_button_click(aid)
{
  var A=document.getElementById("compile")

  var url=A.value;
  url=url.replace(/%/g,"%25")
/*
  url="http://192.168.2.128/compile/"+url
*/
  url="compile/"+url
  console.log(url);
   
  document.getElementById("iframepage").src=url;
  bodyframe.style.visibility='visible';

}


function summary_button_click(aid)
{
  var A=document.getElementById("summary")

  var url=A.value;
  url=url.replace(/%/g,"%25")
/*
  url="http://192.168.2.128/summary/"+url
*/
  url="summary/"+url
  console.log(url);
   
  document.getElementById("iframepage").src=url;
  bodyframe.style.visibility='visible';

}



    function iFrameHeight() {

        var ifm= document.getElementById("iframepage");

        var subWeb = document.frames ? document.frames["iframepage"].document :

ifm.contentDocument;

            if(ifm != null && subWeb != null) {

            ifm.height = subWeb.body.scrollHeight;

            }

    }







</script>

</head>




<body onload="javascript:all_init();">

<h2>android lkp 结果展示:</h2>


<h3>csv 曲线<h3>
  <form action="./lkp_web_oto/getMETRIC.php" enctype="multipart/form-data" method="post" accept-charset="utf-8">
<select id="benchmarks" name="benchmarks" >

                        </select>

                        <input class="btn btn-success" type="submit" value="SUBMIT" >
                        <input class="btn btn-inverse" type="reset" value="CLEAR" >
                        </form>



<h3>testcase信息展示[筛选法]</h3>

<div id="entry" class="ui-widget">

</div>
<div id="result"></div>

<script id="entry-template"  type="text/x-handlebars-template">
    <div id="1div">
    <label>筛选:</label>
    <select name="select_label"  class="easyui-combobox"  name="state" id="1" onchange="click_select_label(this)" >
        {{#with init_tags}}
        {{#each this}}
        <option value="{{this}}" >{{this}}</option>
        {{/each}}
        {{/with}}

    </select>
    </div>
</script>

<script id="select-template" type="text/x-handlebars-template">
    {{#with tags}}
    {{#each this}}
    <option value="{{this}}" >{{this}}</option>

    {{/each}}
    {{/with}}
</script>





</br>
<h3>testcase信息展示:</h3>
<form action="testcase_form.php" method="get">
testcase <select id="testcase" name="testcase"  onchange="changeselect(this.id)">
</select>

test_config <select id="test_config" name="test_config" onchange="changeselect(this.id)">

</select>

tbox_group <select id="tbox_group" name="tbox_group" onchange="changeselect(this.id)">

</select>

rootfs <select id="rootfs"  name="rootfs" onchange="changeselect(this.id)">

</select>


kconfig <select id="kconfig" name="kconfig"  onchange="changeselect(this.id)">

</select>


compiler <select id="compiler"  name="compiler" onchange="changeselect(this.id)">

</select>

 commit<select id="commit"  name="commit"    onchange="changeselect(this.id)">

</select>

run <select id="run"  name="run"onchange="changeselect(this.id)">

</select>
</br>
<input type="submit" value="Submit">
</form>

</br>
<h3> compile 信息展示</h3>
<form action="compile_form.php" method="get">
compile  <select id="compile" name="compile"  onchange="changeselect(this.id)">
</select>
</br>
<input type="submit" value="Submit">
</form>





</br>
<h3> summary 信息展示</h3>
<form action="summary_form.php" method="get">
summary  <select id="summary" name="summary"  onchange="changeselect(this.id)">
</select>

</br>
<input type="submit" value="Submit">
</form>

</br>




</body>
</html>
