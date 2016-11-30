    var html    = template(context);
        $("#entry").html(html);
        click_select_label(this);

       //获取url参数
        var url = parent.window.location.search;
        var args = {};
        if (url.indexOf('?') != -1) {
            var str = url.substr(1);
            var arglist = str.split('&');
            for (var i in arglist) {
                argstr = arglist[i];
                if (argstr != null & argstr != '') {
                    var key = argstr.split('=')[0];
                    var value = argstr.split('=')[1];
                    if (args[key] == undefined) {
                        args[key] = [];
                    }
                    args[key].push(unescape(value));
                }
            }
        }
        //参数中分离出tags    url=……?tags=arg1,arg2,arg3
        var tags=args["tags"];
        var file=[]

        tags=tags[0].split(",");
        if(tags.length>0){
            //筛选结果
            $.each(context.detail,function(n,value) {
                for(var i=0;i<value.length;i++){
                    if (isSubSet(tags, value[i].tags)==1){
                        var temp={};
                        temp["path"]=value[i].path;
                        temp["file"]=n;
                        file.push(temp);
                    }
                }
            });
            var s=""
             var temp=[];
     $.each(file,function(n,value) {
       temp.push(value.path);
 });
    temp.sort().reverse();
   
$.each(temp,function(n,value) {
 value=value.replace(/%/g,"%25");  

s+="</br><a href=./result"+value+">result"+value+"</a>"});

//            $.each(file,function(n,value) {s+="</br><a href=http://192.168.2.128/result/"+value.path+">result"+value.path+"</a>"});
            document.getElementById("result").innerHTML =s;

        }





    }
);



/***
 * 点击选择框
 * @param obj
 * 根据目前选择框的内容生成下一个选择框
 */
function click_select_label(obj){

    //next_id为生成的下一个选择框id
    var next_id=obj.id+"1";
    //select_ids为目前选中的所有选择框的id列表
    var select_ids=[];

    for(var i=0;i<next_id.length-1;i++){
        select_ids.push(next_id.substr(0,i+1));
    }
    //根据目前选中的内容，生成下一个选择框的可选标签
    var next_data={"tags":gen_next_list (select_ids ,context)};
    //生成下一个选择框(参数为当前选择框的id,下一个选择框的id)
    addNextSibling(obj.id,next_id);
    //生成下一个选择框的option
    var source_select = $("#select-template").html();
    var template_select = Handlebars.compile(source_select);
    var html_select = template_select(next_data);
    var ID="#"+next_id;
    $(ID).html(html_select);


}

/***
 * 生成下一个选择框
 * @param nodeId ：当前选择框id
 * @param select_id:下一个选择框id
 */
function addNextSibling(nodeId,select_id) {

    //删除当前节点之后的所有兄弟节点
    $("#"+nodeId).nextAll().remove();
    var currentNode = document.getElementById(nodeId);
    var myDiv=document.createElement("div");
    myDiv.setAttribute("id",select_id+"div");

    //添加 select节点
    var mySelect = document.createElement("select");
    //设置 div 属性，如 id
    mySelect.setAttribute("id",select_id);
    mySelect.setAttribute("class","easyui-combobox");

    currentNode.parentNode.appendChild( mySelect);

    //为生成的选择框添加onchange事件，递归调用click_select_label
    document.getElementById(select_id).onchange=function(){
       click_select_label(this)
    };



}

/***
 * 生成下一个选择框的option
 * @param select_ids:目前己选择的所有选择框id列表
 * @param context：目录树对应的json数据
 * @returns {Array}：下一个选择框的option内容
 */
function gen_next_list (select_ids ,context){
    //tags:己选中的标签列表
    var tags=[];
    //next_tags:包含tags的所有标签集的集合
    var next_tags=[];
    //file:包含tags的所有文件的文件名和路径
    var file=[];
    for(var i=0;i<select_ids.length;i++){

        tags.push($("#"+select_ids[i]).val());
    }
    //遍历context,针对所有文件的标签集（tags），判断已选中的标签列表tags是否是该文件对应的标签集的子集，如果是则把文件名和路径包存于file[]中
    $.each(context.detail,function(n,value) {
       for(var i=0;i<value.length;i++){
               if (isSubSet(tags, value[i].tags)==1){
                   next_tags.push(value[i].tags);
                   var temp={};
                   temp["path"]=value[i].path;
                   temp["file"]=n;
                   file.push(temp);
               }
       }
    });
    //next:下一个选择框对应的标签集（没有去除己选择过的标签）
    var next=[]
    for(var i=0;i<next_tags.length;i++){
        next=mergeArray(next,next_tags[i]);
    }
    //result:下一个选择框对应的标签集（没有去除己选择过的标签）
    var result=[]
    for(var i=0;i<next.length;i++){
        var flag=0;
        for(var j=0;j<tags.length;j++){
            if(next[i]==tags[j]){
                flag=1;
            }
        }
        if(flag==0){
            result.push(next[i]);
        }
    }
    
    // 输出当前筛选条件下的结果。
    var s="";
      var temp=[];
     $.each(file,function(n,value) {
       temp.push(value.path);
 });
    temp.sort().reverse();
$.each(temp,function(n,value) {
  value=value.replace(/%/g,"%25");
s+="</br><a href=./result"+value+">result"+value+"</a>"});
//$.each(file,function(n,value) {s+="</br><a href=http://192.168.2.128/result/"+value.path+">result"+value.path+"</a>"});
    document.getElementById("result").innerHTML =s;
      result.sort().reverse();
     result.unshift("---Please Select---"); 
     
    return result;
}
/***
 * 判断子集的函数
 * 判断数组arr1是否为arr2的子集，是则返回1，不是返回0
 ***/
function isSubSet(arr1,arr2){
    var flag=0
    for(var i=0;i<arr1.length;i++){
        var tt=0
        for (var j=0;j<arr2.length;j++){

            if(arr1[i]==arr2[j])
            {
                flag+=1;
                tt=1;
                break;
            }
        }
        if(tt==0){
            return 0;
        }
    }
    if (flag==arr1.length){
        return 1;
    }
    else return 0;
}

/***
 * 合并数组的函数
 * 数组arr1与arr2合并，结果是把arr2合并到arr1,返回arr1
 ***/
function mergeArray(arr1, arr2) {
    var dup;
    for (var i = 0; i < arr2.length; i++){
        dup = false;
        for ( var j = 0;j < arr1.length; j++){
            if (arr1[j] == arr2[i]){
                dup = true;
                break;
            }
        }
        if (!dup){
            arr1.push(arr2[i]);
        }
    }
    return arr1;
}
