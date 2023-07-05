/*
    clousure JavaScript (encapsular variables dentro de JS)
*/

function loginUC (repository) {
    

    async  function exec (username, password) {
        const resp = await repository(username, password);
        const jwt = await resp.text();
        return jwtAdapter(jwt);
    }

    return exec;
}

