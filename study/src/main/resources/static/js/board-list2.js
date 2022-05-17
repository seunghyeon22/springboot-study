/**
 * 
 */
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
		},
		error : function(){
			alert("비동기 처리 오류");
		}
		
	})
}
// 페이지의 데이터 가져오기
function getBoardList(data){
	// append를 사용할 수 도 있기 때문에 반복문으로 사용
	// innerHTML을 사용하면 필요하지 않은 반복문
	while(boardListTable.hasChildNodes()){
		boardListTable.removeChild(boardListTable.firstChild);	
	}
	let tableStr =`
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회수</th>
	</tr>`;
	for(let i=0;i<data.length;i++){
		tableStr+=`
		<tr class="board-items">
			<td>${data[i].boardCode}</td>
			<td>${data[i].title}</td>
			<td>${data[i].username}</td>
			<td>${data[i].boardCount}</td>
		</tr>	`;
	}
	boardListTable.innerHTML=tableStr;
}
for(let i=0; i<boardListButton.length;i++){
	boardListButton[i].onclick=()=>{
		nowPage = boardListButton[i].textContent;
		load(nowPage);
	}
}






