const Router = {
  routes: {},
  init(){
    window.addEventListener('hashchange', ()=> this.resolve());
  },
  register(path, handler){ this.routes[path] = handler },
  resolve(){
    const hash = location.hash.replace('#','') || '/';
    const [path, query] = hash.split('?');
    const h = this.routes[path] || this.routes['/404'];
    if(h) h(Object.fromEntries(new URLSearchParams(query||'')));
  }
};
export default Router;

