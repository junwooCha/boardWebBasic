function isDelCmt(iboard, icmt){
    console.log(icmt);

    if(confirm('댓글을 삭제하시겠습니까?')){
      location.href = '/board/cmt/del?iboard=' + iboard + '&icmt=' + icmt;
    }

}
var cmtModContainerElem = document.querySelector('.cmtModContainer');
var btnCancelElem = cmtModContainerElem.querySelector('#btnCancel');
btnCancelElem.addEventListener('click', function (){
    cmtModContainerElem.style.display = 'none';
});
function openModForm(icmt, ctnt){
    cmtModContainerElem.style.display = 'flex';
    var cmtModFrmElem = cmtModContainerElem.querySelector('#cmtModFrm');
    cmtModFrmElem.icmt.value = icmt;
    cmtModFrmElem.ctnt.value = ctnt;
}