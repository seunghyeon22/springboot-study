const submitBtn = document.querySelector(".submit-btn");
const inputItems = document.querySelectorAll(".input-items");
const textareaItem = document.querySelector(".textarea-item");
/*
	Promise 객체
*/
/*function test(data) {
	return new Promise((resolve, reject) => {

		if (data > 100) {
			resolve(data);
		} else {
			throw reject(new Error("data가 100보다 작거나 같습니다."));
		}

	});
}
test(500)
	.then(testData => testData + 100)//resolve
	.then(testData2 => alert(testData2))
	.catch(error => { console.log(error) })//reject
*/

let path = window.location.pathname;
let boardCode =path.substring(path.lastIndexOf("/")+1);

load();

function load(){ //ajax 패치로 바꾸기
	let url = `/api/board/${boardCode}`;
	fetch(url)
	.then(response =>{
		console.log(response)
		if(response.ok){
		return response.json();
		}else{
			throw new Error(response.json());
		}
	})
	.then(result =>{
		getBoardDtl(result.data);
	})
	.catch(error =>{console.log(error);
	});

/*	$.ajax({
		type : "get",
		url : `/api/board/${boardCode}`,
		dataType : "text",
		success : function(data){
			alert(data);
			let boardObject = JSON.parse(data);
			getBoardDtl(boardObject.data);
		},
		error : function(){
			alert("비동기 처리 오류");
		}
		
	});*/
}
function getBoardDtl(data){
	inputItems[0].value = data.title;
	inputItems[1].value = data.username;
	textareaItem.value = data.content;
}


submitBtn.onclick = () => {
	submit();
}
//ajax 사용
/*
function submit() {
	$.ajax({
		type: "post",
		url: "/board",
		data: JSON.stringify({
			title: inputItems[0].value,
			content: textareaItem.value,
			usercode: inputItems[1].value
		}),
		contentType: "application/json",
		dataType: "text",
		success: data => { //람다식, 매개변수가 하나면 괄화 필요없음
			let dataObj = JSON.parse(data);
			alert(dataObj.msg);
			location.href = "/board/dtl/" + dataObj.data;
		},
		error: () => {
			alert("비동기 처리 오류")
		}
	})
}*/

//fetch 사용
function submit() {
	let url = `/api/board/${boardCode}`;

	let option = {
		method: "put",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			title: inputItems[0].value,
			content: textareaItem.value
		})

	}
	fetch(url,option)
	.then(response =>{
		console.log(response)
		if(response.ok){
			return response.json();
		}else{
			throw new Error(response.json()/*정상적인 데이터를 응답받지 못했습니다.*/); 
		}
		
	})
	.then(data => {location.href ="/board-info/"+data.data})
	.catch(error => console.log(error));
}


