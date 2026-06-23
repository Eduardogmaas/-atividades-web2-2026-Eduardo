async function carregar(){
 const r=await fetch('/transacoes');
 const dados=await r.json();
 let html='<tr><th>ID</th><th>Descrição</th><th>Valor</th><th>Tipo</th></tr>';
 dados.forEach(t=>html+=`<tr><td>${t.id}</td><td>${t.descricao}</td><td>${t.valor}</td><td>${t.tipo}</td></tr>`);
 document.getElementById('tabela').innerHTML=html;
}
async function salvar(){
 await fetch('/transacoes',{
  method:'POST',
  headers:{'Content-Type':'application/json'},
  body:JSON.stringify({
   descricao:descricao.value,
   valor:Number(valor.value),
   tipo:tipo.value
  })
 });
 carregar();
}
carregar();