var frmElem = document.querySelector('#frm');
var submitBtnElem = document.querySelector('#submitBtn');

submitBtnElem.addEventListener('click', function (){
   if(frm.upw.value.length < 5){
      alert("현재비밀번호를 확인하세요.");
   }else if(frm.changedUpw.value.length < 5){
      alert("변경비밀번호를 확인하세요.");
   }else if(frm.changedUpw.value != frm.changedUpwConfirm.value){
      alert("변경 비밀번호와 확인 비밀번호가 다릅니다.");
   }else {
      frmElem.submit();
   }
});
