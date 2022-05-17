const boardListTable = document.querySelector(".board-list-table");
const boardListPage = document.querySelector(".board-list-page");
const boardListButton =boardListPage.querySelectorAll('div');




let nowPage =1;

load(nowPage);

function load(page){
	$.ajax({
		type : "get",
		url : "/board/list",
		data : {
			"page" : page
		},
		dataType : "text",
		success : function(data){
			console.log(data);
			let boardList = JSON.parse(data);
			getBoardList(boardList.data);
			getBoardItems();
		},
		error : function(){
			alert("비동기 처리 오류");
		}
		
	})
}
// 페이지의 데이터 가져오기
function getBoardList(data){
	const tableBody = boardListTable.querySelector('tbody');
	let tableStr =``;
	for(let i=0;i<data.length;i++){
		tableStr+=`
		<tr class="board-items">
			<td>${data[i].boardCode}</td>
			<td>${data[i].title}</td>
			<td>${data[i].username}</td>
			<td>${data[i].boardCount}</td>
		</tr>	`
	}
	tableBody.innerHTML=tableStr;
}
for(let i=0; i<boardListButton.length;i++){
	boardListButton[i].onclick=()=>{
		nowPage = boardListButton[i].textContent;
		load(nowPage);
	}
}
function getBoardItems(){
	const boardItems = document.querySelectorAll(".board-items");
	console.log(boardItems);
	for (let i = 0; i < boardItems.length; i++) {
		boardItems[i].onclick = () => {
			location.href="/board/dtl/"+boardItems[i].querySelectorAll('td')[0].textContent;
		}
	}
	
}





