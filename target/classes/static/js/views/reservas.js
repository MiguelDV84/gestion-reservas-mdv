import API from '../api.js';
import {el, bindForm, notify} from '../ui.js';

function ReservaForm(r){
  const user = API.currentUser();
  const f = el(`<form class="form">
    <label>Motivo<input name="motivo" value="${r?.motivo||''}" required /></label>
    <label>Nº asistentes<input name="numAsistentes" type="number" min="3" value="${r?.numAsistentes||''}" required /></label>
    <label>Fecha reserva<input name="fechaReserva" type="date" value="${r?.fechaReserva||''}" required /></label>
    <label>Aula<select name="aulaId"><option value="">Cargando aulas...</option></select></label>
    <label>Tramo horario<select name="tramoId"><option value="">Cargando tramos...</option></select></label>
    <input type="hidden" name="usuarioId" value="${r?.usuario?.id || user?.id || ''}" />
    <div class="actions"><button class="btn" type="submit">Guardar</button></div>
  </form>`);

  // Cargar listas de aulas y tramos, y preseleccionar si aplica
  (async ()=>{
    try{
      const [aulas, tramos] = await Promise.all([API.aulas.list(), API.tramos.list()]);
      const selAula = f.querySelector('select[name=aulaId]');
      const selTramo = f.querySelector('select[name=tramoId]');
      selAula.innerHTML = '';
      aulas.forEach(a=>{
        const opt = document.createElement('option');
        opt.value = a.id; opt.textContent = `${a.nombre} (cap ${a.capacidad})`;
        if(r?.aula?.id && r.aula.id === a.id) opt.selected = true;
        selAula.appendChild(opt);
      });
      selTramo.innerHTML = '';
      tramos.forEach(t=>{
        const opt = document.createElement('option');
        opt.value = t.id; opt.textContent = `${t.diaSemana} ${t.horaInicio} - ${t.horaFin} (${t.tipoTramo})`;
        if(r?.tramo?.id && r.tramo.id === t.id) opt.selected = true;
        selTramo.appendChild(opt);
      });
    }catch(e){
      notify('Error cargando aulas/tramos: ' + e.message, 'error');
    }
  })();

  return f;
}

export default function Reservas(){
  const wrap = el(`<div class="panel"><h2>Reservas</h2>
    <div class="panel"><button class="icon-btn" id="btnMine">Solo mis reservas</button> <button class="icon-btn" id="btnAll">Todas</button></div>
    <div class="list" id="list"></div>
    <h3>Nueva Reserva</h3>
    <div id="form"></div>
  </div>`);
  const list = wrap.querySelector('#list');
  const formWrap = wrap.querySelector('#form');

  const render = async (mine=false)=>{
    list.innerHTML = '<div class="small">Cargando...</div>';
    try{
      const user = API.currentUser();
      const data = mine && user?.id ? await API.reservas.listByUsuario(user.id) : await API.reservas.list();
      list.innerHTML = '';
      data.forEach(r=>{
        const item = el(`<div class="item">
          <div><strong>${r.motivo||'Reserva'}</strong> <span class="badge">${r.fechaReserva}</span> • Aula: ${r.aula?.nombre} • Usuario: ${r.usuario?.email}</div>
          <div class="actions-row">
            <button class="icon-btn ok" data-edit>Editar</button>
            <button class="icon-btn danger" data-del>Eliminar</button>
          </div>
        </div>`);
        item.querySelector('[data-edit]').onclick = async()=>{
          const detail = await API.reservas.get(r.id);
          formWrap.innerHTML = '';
          const form = ReservaForm(detail);
          bindForm(form, async (data)=>{
            data.aulaId = Number(data.aulaId); data.tramoId = Number(data.tramoId); data.usuarioId = Number(data.usuarioId); data.numAsistentes = Number(data.numAsistentes);
            await API.reservas.update(r.id, data);
            notify('Reserva actualizada');
            render(mine);
          });
          formWrap.appendChild(form);
        };
        item.querySelector('[data-del]').onclick = async()=>{
          if(!confirm('Eliminar reserva?')) return;
          await API.reservas.del(r.id);
          notify('Reserva eliminada');
          render(mine);
        }
        list.appendChild(item);
      })
    }catch(e){ list.innerHTML = '<div class="error">'+e.message+'</div>' }
  };

  const createForm = ReservaForm();
  bindForm(createForm, async (data)=>{
    data.aulaId = Number(data.aulaId); data.tramoId = Number(data.tramoId); data.usuarioId = Number(data.usuarioId); data.numAsistentes = Number(data.numAsistentes);
    await API.reservas.insert(data);
    notify('Reserva creada');
    createForm.reset();
    render();
  });
  formWrap.appendChild(createForm);

  wrap.querySelector('#btnMine').onclick = ()=> render(true);
  wrap.querySelector('#btnAll').onclick = ()=> render(false);

  render();
  return wrap;
}
