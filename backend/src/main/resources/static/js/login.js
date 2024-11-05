const form = document.getElementById('form');
form.addEventListener('keydown', (e) => keyDownEnter(e));

const button = document.getElementById('login-button');
button.addEventListener('click', (e) => clickButton(e));

const clickButton = (e) => {
  e.preventDefault();
  loginRequest();
}

const keyDownEnter = (e) => {
  if (e.key === 'Enter') {
    e.preventDefault();
    loginRequest();
  }
}

const extractLoginData = () => {
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  return {
    email: email,
    password: password
  };
}

const loginRequest = () => {
  const loginData = extractLoginData();
  axios.post('http://localhost:8080/admin/accounts/signin', loginData)
  .then(res => {
    if (res.status === 200) {
      window.location.href = 'http://localhost:8080/admin';
    }
  });
}

