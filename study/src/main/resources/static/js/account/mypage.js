/**
 * user 객체 가져와서 data넣기
 */
const usernameText = document.querySelector(".username-text");
const inputItems = document.querySelectorAll(".text-inputs");
const fileInput = document.querySelector(".file-input");
const profileImgUrl = document.querySelector(".profile-img-url");


async function imgSubmit(){
	let formData =new FormData(document.querySelector("form"));
	const url = "/api/v1/user/account/profile/img";
	
	const option = {
		method : "put",
		headers : {},
		body: formData
	};
	const response = await fetch(url,option);
	if(response.ok){
		alert("프로필 이미지 변경이 되었습니다.")
		return response.json();
	}else{
		throw new Error("Failed to upload img");
	}
}


fileInput.onchange = () => {
	let reader = new FileReader();
	
	reader.onload=(e)=>{
			profileImgUrl.src=e.target.result;
			if(confirm("이미지를 변경하시겠습니까?")){				
			const result = imgSubmit();
			console.log(JSON.stringify(result));
			}
	}
	reader.readAsDataURL(fileInput.files[0]);
}



getAuthenticationReq()
	.then(result => {
		let principal = result.data.user
		usernameText.textContent = principal.username;
		inputItems[0].value = principal.username;
		inputItems[1].value = principal.email;
		inputItems[2].value = principal.name;
		profileImgUrl.src = "/image/profile/" + principal.profile_img_url;
	})
	.catch(error => {
		console.log(error);
	})

