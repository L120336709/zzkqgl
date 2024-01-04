function showTeacher(){
    $.modal.open("选择教师" , "/kqgl/teacherinfo/teacherlist","","",function(index, layero) {
        var arr = $(layero).find('iframe')[0].contentWindow.TongBu();//调用子页面的方法
        var arrs=arr.split("|")
        if(arrs[0]==0){
            alert("请选择一名教师！")
            return
        }else if(arrs[0] > 1){
            alert("只能选择一名教师！")
            return;
        }
        document.getElementById("teacherName").value=arrs[2]
        document.getElementById("teacherId").value=arrs[1]
        parent.layer.close(index);
    });
}


function checkTeacher(teacherId){
    var s=0;
    $.ajax({
        async:false,
        type: "get",
        url: "/kqgl/class/list",
        data: {
            "teacherId": teacherId
        },
        success: function (data) {
            if(data.total>0){
                s=data.rows[0].id
            }
        }
    })
    return s
}
function showGrade(){



    $.modal.open("选择班级" , "/kqgl/gradeinfo","","",function(index, layero) {
        var arr = $(layero).find('iframe')[0].contentWindow.GradeMes();//调用子页面的方法
        var arrs=arr.split("|")
        if(arrs[0]==0){
            alert("请选择一个班级！")
            return
        }else if(arrs[0] > 1){
            alert("只能选择一个班级！")
            return;
        }
        document.getElementById("gradeName").value=arrs[1]
        document.getElementById("gradeId").value=arrs[2]

            parent.layer.close(index);

    });
}


function showClass(){

    var isMobile = /iPhone|iPad|iPod|Android/i.test(navigator.userAgent);

    var url="/kqgl/class/classlist"
    if (isMobile) {
        url="/kqgl/class/classlistphone"
        console.log('当前在手机端');
    } else {
        console.log('当前在PC端');
    }


    $.modal.open("选择班级" , url,"","",function(index, layero) {
        var arr = $(layero).find('iframe')[0].contentWindow.TongBu();//调用子页面的方法
        var arrs=arr.split("|")
        if(arrs[0]==0){
            alert("请选择一个班级！")
            return
        }else if(arrs[0] > 1){
            alert("只能选择一个班级！")
            return;
        }
        document.getElementById("className").value=arrs[2]
        document.getElementById("classId").value=arrs[1]
        if (isMobile) {
            parent.layer.close(index);
        } else {
            parent.layer.close(index);
        }
    });
}

function getName(){
    var teacherId=document.getElementById("teacherId").value
    $.ajax({
        type: "post",
        url: "/kqgl/teacherinfo/getTeacherById",
        data: {
            "teacherId": teacherId
        },
        success: function (zzTeacherinfo) {
            if(zzTeacherinfo!=null){
                document.getElementById("teacherName").value=zzTeacherinfo.personName
            }
        }
    })

    var classId=document.getElementById("classId").value
    $.ajax({
        type: "post",
        url: "/kqgl/classinfo/getclassById",
        data: {
            "classId": classId
        },
        success: function (zzClassinfo) {
            if(zzClassinfo!=null){
                document.getElementById("className").value=zzClassinfo.gradeName+zzClassinfo.className
            }
        }
    })


}