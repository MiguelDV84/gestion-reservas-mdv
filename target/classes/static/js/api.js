const API = {
  base: '', // mismo origen
  tokenKey: 'auth_token',
  get token(){ return localStorage.getItem(this.tokenKey) || '' },
  set token(v){ if(v) localStorage.setItem(this.tokenKey, v); else localStorage.removeItem(this.tokenKey) },
  headers(json=true){
    const h = {};
    if(this.token) h['Authorization'] = 'Bearer ' + this.token;
    if(json) h['Content-Type'] = 'application/json';
    return h;
  },
  async req(path, opts={}){
    const res = await fetch(this.base + path, opts);
    if(res.status === 401){
      this.token = '';
      if(location.hash !== '#/login') location.hash = '#/login';
      throw new Error('No autorizado');
    }
    if(!res.ok){
      const text = await res.text();
      throw new Error(text || res.statusText);
    }
    const ct = res.headers.get('content-type') || '';
    return ct.includes('application/json') ? res.json() : res.text();
  },
  // auth
  login: (email,password)=> API.req('/auth/login',{method:'POST',headers:API.headers(),body:JSON.stringify({email,password})}),
  register: (payload)=> API.req('/auth/register',{method:'POST',headers:API.headers(),body:JSON.stringify(payload)}),

  // aulas
  aulas:{
    list: ()=> API.req('/aula/list'),
    get: (id)=> API.req('/aula/'+id),
    insert: (payload)=> API.req('/aula/insert',{method:'POST',headers:API.headers(),body:JSON.stringify(payload)}),
    update: (id,payload)=> API.req('/aula/update/'+id,{method:'PUT',headers:API.headers(),body:JSON.stringify(payload)}),
    del: (id)=> API.req('/aula/delete/'+id,{method:'DELETE',headers:API.headers(false)}),
    listByNombre: (nombre)=> API.req('/aula/list/'+encodeURIComponent(nombre)),
    listOrdenadores: ()=> API.req('/aula/list/ordenadores'),
    listNoOrdenadores: ()=> API.req('/aula/list/no-ordenadores'),
    listCapacidad: (capacidad)=> API.req('/aula/list/capacidad/'+Number(capacidad)),
    withReservas: (id)=> API.req('/aula/with-reservas/'+id),
  },
  // tramos
  tramos:{
    list: ()=> API.req('/tramo-horario/list'),
    get: (id)=> API.req('/tramo-horario/'+id),
    insert: (payload)=> API.req('/tramo-horario/insert',{method:'POST',headers:API.headers(),body:JSON.stringify(payload)}),
    update: (id,payload)=> API.req('/tramo-horario/update/'+id,{method:'PUT',headers:API.headers(),body:JSON.stringify(payload)}),
    del: (id)=> API.req('/tramo-horario/delete/'+id,{method:'DELETE',headers:API.headers(false)}),
  },
  // reservas
  reservas:{
    list: ()=> API.req('/reserva/list'),
    listByUsuario: (id)=> API.req('/reserva/list-usuario/'+id),
    get: (id)=> API.req('/reserva/'+id),
    insert: (payload)=> API.req('/reserva/insert',{method:'POST',headers:API.headers(),body:JSON.stringify(payload)}),
    update: (id,payload)=> API.req('/reserva/update/'+id,{method:'PUT',headers:API.headers(),body:JSON.stringify(payload)}),
    del: (id)=> API.req('/reserva/delete/'+id,{method:'DELETE',headers:API.headers(false)}),
  },
  // usuarios
  usuarios:{
    list: ()=> API.req('/usuario/list'),
    get: (id)=> API.req('/usuario/'+id),
    listByName: (name)=> API.req('/usuario/list-name/'+encodeURIComponent(name)),
    getByEmail: (email)=> API.req('/usuario/list-email/'+encodeURIComponent(email)),
    update: (id,payload)=> API.req('/usuario/update/'+id,{method:'PUT',headers:API.headers(),body:JSON.stringify(payload)}),
    del: (id)=> API.req('/usuario/delete/'+id,{method:'DELETE',headers:API.headers(false)}),
  },
  parseJwt(token){
    if(!token) return null;
    const base = token.split('.')[1];
    const json = atob(base.replace(/-/g,'+').replace(/_/g,'/'));
    try{ return JSON.parse(decodeURIComponent(escape(json))); }catch{ return JSON.parse(json) }
  },
  currentUser(){ return this.parseJwt(this.token) },
};

export default API;
