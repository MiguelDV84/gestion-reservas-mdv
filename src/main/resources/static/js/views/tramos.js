import API from '../api.js';
import {el, bindForm, notify} from '../ui.js';

function normTime(t){ return t && t.length===5 ? `${t}:00` : t }

function TramoForm(t){
  const f = el(`<form class="form">
    <label>Día<select name="diaSemana">
      <option>LUNES</option><option>MARTES</option><option>MIERCOLES</option><option>JUEVES</option><option>VIERNES</option>
    </select></label>
    <label>Inicio<input name="horaInicio" type="time" value="${t?.horaInicio||''}" required /></label>
    <label>Fin<input name="horaFin" type="time" value="${t?.horaFin||''}" required /></label>
    <label>Tipo<select name="tipoTramo"><option>LECTIVO</option><option>RECREO</option><option>MEDIO_DIA</option></select></label>
    <label>Aula ID<input name="aulaId" type="number" value="${t?.aulaId||''}" /></label>
    <div class="actions"><button class="btn" type="submit">Guardar</button></div>
  </form>`);
  if(t){ f.querySelector('select[name=diaSemana]').value = t.diaSemana; f.querySelector('select[name=tipoTramo]').value = t.tipoTramo; }
  return f;
}

export default function Tramos(){
  const wrap = el(`<div class="panel"><h2>Tramos Horarios</h2>
    <div class="list" id="list"></div>
    <h3>Nuevo Tramo</h3>
    <div id="form"></div>
  </div>`);
  const list = wrap.querySelector('#list');
  const formWrap = wrap.querySelector('#form');

  const render = async ()=>{
    list.innerHTML = '<div class="small">Cargando...</div>';
    try{
      const data = await API.tramos.list();
      list.innerHTML = '';
      data.forEach(t=>{
        const item = el(`<div class="item">
          <div><strong>${t.diaSemana}</strong> ${t.horaInicio} - ${t.horaFin} <span class="badge">${t.tipoTramo}</span></div>
          <div class="actions-row">
            <button class="icon-btn ok" data-edit>Editar</button>
            <button class="icon-btn danger" data-del>Eliminar</button>
          </div>
        </div>`);
        item.querySelector('[data-edit]').onclick = async ()=>{
          const detail = await API.tramos.get(t.id);
          formWrap.innerHTML = '';
          const form = TramoForm(detail);
          bindForm(form, async (data)=>{
            data.aulaId = data.aulaId? Number(data.aulaId): null;
            data.horaInicio = normTime(data.horaInicio);
            data.horaFin = normTime(data.horaFin);
            await API.tramos.update(t.id, data);
            notify('Tramo actualizado');
            render();
          });
          formWrap.appendChild(form);
        };
        item.querySelector('[data-del]').onclick = async ()=>{
          if(!confirm('Eliminar tramo?')) return;
          await API.tramos.del(t.id);
          notify('Tramo eliminado');
          render();
        }
        list.appendChild(item);
      })
    }catch(e){ list.innerHTML = '<div class="error">'+e.message+'</div>' }
  };

  const createForm = TramoForm();
  bindForm(createForm, async (data)=>{
    data.aulaId = data.aulaId? Number(data.aulaId): null;
    data.horaInicio = normTime(data.horaInicio);
    data.horaFin = normTime(data.horaFin);
    await API.tramos.insert(data);
    notify('Tramo creado');
    createForm.reset();
    render();
  });
  formWrap.appendChild(createForm);

  render();
  return wrap;
}
