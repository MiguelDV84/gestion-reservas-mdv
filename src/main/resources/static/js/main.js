import Router from './router.js';
import API from './api.js';
import {setContent} from './ui.js';
import {LoginView, RegisterView} from './views/auth.js';
import Dashboard from './views/dashboard.js';
import Aulas from './views/aulas.js';
import Tramos from './views/tramos.js';
import Reservas from './views/reservas.js';
import Usuarios from './views/usuarios.js';

const content = document.getElementById('content');
const nav = document.getElementById('nav');

function mount(view){
  setContent(content, '');
  content.appendChild(view);
}

function setAuthUi(){
  const logged = !!API.token;
  document.getElementById('btn-login').style.display = logged? 'none':'inline-block';
  document.getElementById('btn-register').style.display = logged? 'none':'inline-block';
  document.getElementById('btn-logout').style.display = logged? 'inline-block':'none';
  const user = API.currentUser();
  document.getElementById('auth-user').textContent = logged? (user?.sub || 'Conectado') : 'Invitado';
}

document.getElementById('btn-logout').onclick = ()=>{ API.token = ''; setAuthUi(); location.hash = '#/login' };

function guard(handler){
  return (params)=>{
    if(!API.token && location.hash !== '#/login' && location.hash !== '#/register'){
      location.hash = '#/login';
      return;
    }
    handler(params);
  }
}

Router.register('/', guard(()=> mount(Dashboard())));
Router.register('/login', ()=> mount(LoginView()));
Router.register('/register', ()=> mount(RegisterView()));
Router.register('/aulas', guard(()=> mount(Aulas())));
Router.register('/tramos', guard(()=> mount(Tramos())));
Router.register('/reservas', guard(()=> mount(Reservas())));
Router.register('/usuarios', guard(()=> mount(Usuarios())));
Router.register('/404', ()=> setContent(content,'<div class="panel">No encontrado</div>'));

window.addEventListener('load', ()=>{
  setAuthUi();
  if(!API.token && (!location.hash || location.hash==='#/')) location.hash = '#/login';
  // activar link activo
  window.addEventListener('hashchange', ()=>{
    const hash = location.hash || '#/';
    [...nav.querySelectorAll('a')].forEach(a=> a.classList.toggle('active', a.getAttribute('href')===hash));
  });
  Router.init();
  Router.resolve();
});
