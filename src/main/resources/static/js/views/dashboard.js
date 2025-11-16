import API from '../api.js';
import {el} from '../ui.js';

export default function Dashboard(){
  const wrap = el(`<div class="panel"><h2>Dashboard</h2>
    <div class="grid">
      <div class="card"><h3>Aulas</h3><div id="k-aulas" class="big">-</div></div>
      <div class="card"><h3>Tramos</h3><div id="k-tramos" class="big">-</div></div>
      <div class="card"><h3>Reservas</h3><div id="k-reservas" class="big">-</div></div>
      <div class="card"><h3>Usuarios</h3><div id="k-usuarios" class="big">-</div></div>
    </div>
  </div>`);
  Promise.allSettled([
    API.aulas.list().then(x=>wrap.querySelector('#k-aulas').textContent = x.length),
    API.tramos.list().then(x=>wrap.querySelector('#k-tramos').textContent = x.length),
    API.reservas.list().then(x=>wrap.querySelector('#k-reservas').textContent = x.length),
    API.usuarios.list().then(x=>wrap.querySelector('#k-usuarios').textContent = x.length),
  ]);
  return wrap;
}

