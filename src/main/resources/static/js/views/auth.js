import API from '../api.js';
import {el, setContent, bindForm, notify} from '../ui.js';

export const LoginView = ()=>{
  const wrap = el(`<div class="panel"><h2>Iniciar sesión</h2>
    <form class="form">
      <label>Email<input name="email" type="email" required /></label>
      <label>Contraseña<input name="password" type="password" required /></label>
      <div class="actions"><button class="btn" type="submit">Entrar</button></div>
    </form>
    <div class="small">¿Sin cuenta? <a href="#/register">Regístrate</a></div>
  </div>`);
  const form = wrap.querySelector('form');
  bindForm(form, async ({email,password})=>{
    const {token} = await API.login(email,password);
    API.token = token;
    document.getElementById('auth-user').textContent = email;
    document.getElementById('btn-login').style.display='none';
    document.getElementById('btn-register').style.display='none';
    notify('Bienvenido');
    location.hash = '#/';
  });
  return wrap;
}

export const RegisterView = ()=>{
  const wrap = el(`<div class="panel"><h2>Registrarse</h2>
    <form class="form">
      <label>Nombre<input name="nombre" required /></label>
      <label>Apellidos<input name="apellidos" required /></label>
      <label>Email<input name="email" type="email" required /></label>
      <label>Contraseña<input name="password" type="password" minlength="3" required /></label>
      <div class="actions"><button class="btn" type="submit">Crear cuenta</button></div>
    </form>
  </div>`);
  const form = wrap.querySelector('form');
  bindForm(form, async (data)=>{
    await API.register(data);
    notify('Usuario registrado');
    location.hash = '#/login';
  });
  return wrap;
}

