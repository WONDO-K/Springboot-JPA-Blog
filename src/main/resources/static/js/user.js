let index={
		init:function(){
			// on함수 btn-save가 1.이벤트 발생 2.어떤 행동을 할 것 인가?
			// let_this=this; 를 적어주면 function()사용 가능
			$("#btn-save").on("click",()=>{ // function(){}을 사용하면 this가 윈도우 함수를
											// 가르키게 된다. ()=>{} this를 바인딩하기 위해
											// 사용하는 것
				this.save();
			});	
			
			$("#btn-login").on("click",()=>{ 
				this.login();
			});	
		},
			save:function(){
				// alert("user의 save함수가 호출됨");
				let data={
						username:$("#username").val(),
						password:$("#password").val(),
						email:$("#email").val()
				};
				// console.log(data);
				
				// ajax요청
				// ajax 호출시 default가 비동기 호출
				// ajax 통신을 이용해서 3개의 데이터(파라미터)를 json으로 변경하여 insert요청
				// ajax가 통신을 성공하고 json을 리턴해주면 서버가 자동으로 자바오브젝트로 변환해준다
				$.ajax({
					type:"POST",
					url:"/blog/api/user",
					data: JSON.stringify(data), // http body 데이터 JSON 문자열 /data
												// : data로 하면 자바스크립트 오브젝트
												
					contentType:"application/json; charset=utf-8", // body데이터가
																	// 어떤
																	// 타입인지(MIME타입)
					dataType  : "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이
										// String(문자열)이지만 생김새가 json이라면 =>
										// javascript 오브젝트로 변경해준다
				}).done(function(resp){ // 요청 성공
					alert("회원가입이 완료!");
					console.log(resp);
					location.href="/blog"; // 수행 후 이동 페이지
				}).fail(function(error){ // 요청 실패
					alert(JSON.stringify(error));
				}); 
		},
		login:function(){
			// alert("user의 save함수가 호출됨");
			let data={
					username:$("#username").val(),
					password:$("#password").val()
			};
			// console.log(data);
			
			// ajax요청
			// ajax 호출시 default가 비동기 호출
			// ajax 통신을 이용해서 3개의 데이터(파라미터)를 json으로 변경하여 insert요청
			// ajax가 통신을 성공하고 json을 리턴해주면 서버가 자동으로 자바오브젝트로 변환해준다
			$.ajax({
				type:"POST",
				url:"/blog/api/user/login",
				data: JSON.stringify(data), // http body 데이터 JSON 문자열 /data :
											// data로 하면 자바스크립트 오브젝트
											
				contentType:"application/json; charset=utf-8", // body데이터가 어떤
																// 타입인지(MIME타입)
				dataType  : "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이
									// String(문자열)이지만 생김새가 json이라면 =>
									// javascript 오브젝트로 변경해준다
			}).done(function(resp){ // 요청 성공
				alert("로그인 완료!");
				console.log(resp);
				location.href="/blog"; // 수행 후 이동 페이지
			}).fail(function(error){ // 요청 실패
				alert(JSON.stringify(error));
			}); 
	}
}

index.init();