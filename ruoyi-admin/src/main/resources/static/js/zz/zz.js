

function closepage(){
   window.location.href="/h5/index2"
}
function closepage2(){
    history.back()
}


//输入框数据变动时，触发函数
$('#receiver').on('input propertychange', function () {
    if ($(this).prop('cnStart')) return;
    getUserOp()
}).on('compositionstart', function () {
    $(this).prop('cnStart', true);
}).on('compositionend', function () {
    $(this).prop('cnStart', false);
    getUserOp()

});

//设置显示select列表中的多少行数据
function setOp(options) {
    var select = document.getElementById("receiverselect");
    select.addEventListener("focus",function(){
        select.size = options;
    });
    select.focus();
}

//选择人员后，把人员id和姓名填入对应位置并隐藏人员列表框
function opClick(op) {
    document.getElementById("receiver").value=document.getElementById(op).innerText
    document.getElementById("receiver0").value=  document.getElementById(op).getAttribute("value")
    document.getElementById("receiverselect").hidden=true
}

//获取用户信息，全部显示
function getUserOp(){
    //获取user信息，填充到option
    var receiver=document.getElementById("receiver").value
    receiver=receiver.replace(/\s*/g,"");
    if(receiver.length==0){
        document.getElementById("receiver0").value=null
        document.getElementById("receiverselect").innerHTML=''
        setOp(0)
        return
    }
    $.ajax({
        cache: true,
        type: "POST",
        url: "/system/user/list",
        data:{"userName":receiver},
        async: false,
        error: function (request) {
            $.modal.alertError("系统错误");
        },
        success: function (data) {
            if(data.code=="0"){
                if(data.total>0){
                    var receiverselect=""
                    for(var i=0;i<data.total;i++){
                        receiverselect=receiverselect+"<div onclick='opClick(\"op"+i+"\")' id='op"+i+"' " +
                            "style='height: 20px;padding: 5px 10px' value='"+data.rows[i].userId+"'>"+data.rows[i].userName+"</div>"
                    }
                    document.getElementById("receiverselect").innerHTML=receiverselect
                }else {

                }
                document.getElementById("receiverselect").hidden=false
                setOp(data.total)
            }else {
                document.getElementById("receiver0").value=null
            }
        }
    })
}

//判断是否需要放入草稿箱
function toMes() {
    layer.confirm('已填写的邮件内容将丢失，是否保存至草稿箱', {
        title:"离开写邮件",
        btn: ['取消', '保存草稿', '离开'] //可以无限个按钮
        ,btn3: function(index, layero){
            window.location.href="/system/message"//离开
        }
    }, function(index, layero){
        layer.close(index)
        //按钮【按钮一】的回调
    }, function(index){
        document.getElementById("in").value="0"
        if ($.validate.form()) {
            $(".opacity_bg").show();
            $.operate.save(prefix + "/add", $('#form-message-add').serialize(), function (back) {
                if(back.code==0){
                    layer.msg("保存至草稿箱成功！",{icon:1,time:2000},function () {
                        window.location.href="/system/message"
                    })
                }else {
                    layer.msg("保存至草稿箱失败！",{icon:2,time:2000})
                }
            })
        }
        //按钮【按钮二】的回调
    });
}


function Shjx() {
    window.location.href="/system/message"
}
function fjx() {
    window.location.href="/system/message/messagefjx"
}
function cgx() {
    window.location.href="/system/message/messagecgx"
}
