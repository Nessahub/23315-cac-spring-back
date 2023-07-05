const url = 'http://localhost:8081';

const getToken = async () => {
    //pedir un token a una api
    ///api/v1/auth
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
    
   //conocer un caso de uso
   const loginUseCase = loginUC(
        loginRepository()
    );

   
    //login(username,password) -> /login/infrastructure/fetch-api.js
    const jwt = await loginUseCase(username, password);
    localStorage.setItem('jwt', JSON.stringify)
    alert(JSON.stringify(jwt));
   
}

const findUsers = () => {
    //pedir un token a una api
    ///api/v1/auth

    const jwtInLS = localStorage.getItem('jwt'); //string!!
    const jwtObj = JSON.parse(jwtInLS); //objeto

    //algo nativo de los browser para  hacer peticiones asincrónicas
    fetch(`${url}/user`,{
        method: 'GET',
        headers: {
            Authorization: `${jwtObj.type} ${jwtObj.jwt}`
        }
     })
        .then(response => response.json())
        .then(data => console.log(data));
        
}

const btn = document.getElementById('btn-gettoken');
btn.addEventListener('click',getToken);

const btnListado = document.getElementById('btn-getListado');
btnListado.addEventListener('click',findUsers);