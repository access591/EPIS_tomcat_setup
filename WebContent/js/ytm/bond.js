// (c)2010 Exstrom Laboratories LLC

function part_period(sd,sm,sy,md,prevpm,nextpm)
{var py=prevpm>0?sy:sy-1;var pm=prevpm>0?prevpm-1:prevpm+12-1;var ny=nextpm<12?sy:sy+1;var nm=nextpm<12?nextpm-1:nextpm-12-1;var PDate=new Date(py,pm,md);var NDate=new Date(ny,nm,md);var SDate=new Date(sy,sm-1,sd);return(NDate.getTime()-SDate.getTime())/(NDate.getTime()-PDate.getTime());}
function pay_periods(apf,sd,sm,sy,md,mm,my)
{var mpp=12/apf;var n=(12*(my-sy)+mm-sm)/mpp;var ni=parseInt(n);if(ni==n)
if(sd==md)
return ni;else
if(sd<md)
return ni+part_period(sd,sm,sy,md,sm-mpp,sm);else
return ni+part_period(sd,sm,sy,md,sm,sm+mpp)-1;else{var dm=(mm-sm)%mpp;var npm=dm>0?sm+dm:sm+dm+mpp;return ni+part_period(sd,sm,sy,md,npm-mpp,npm);}}
function bond_price(ytm,interest,m,n)
{var i=interest/m;var x=1/(1+ytm/m);var xn=Math.pow(x,n);var ni=parseInt(n)==n?n-1:parseInt(n);var nf=n-ni;var price;if(ytm==0)
price=1+i*(ni+1);else
price=xn+i*(Math.pow(x,nf)-x*xn)/(1-x);return price;}
function bond_ytm(pf,interest,m,n)
{var i=interest/m;var ni=parseInt(n)==n?n-1:parseInt(n);var nf=n-ni;var x0=0.5;var x=newt_root(x0,ytmpoly(ni,nf,pf,i),dytmpoly(ni,nf,pf,i),0.000001);return m*(1/x-1);}
function ytmpoly(ni,nf,pf,i)
{var f=function(x)
{var xi=Math.pow(x,ni);var xf=nf==1?x:Math.pow(x,nf);return(((i+1)*x-1)*xi-i)*xf-pf*(x-1);};return f;}
function dytmpoly(ni,nf,pf,i)
{var f=function(x)
{var xi=Math.pow(x,ni);var xf=nf==1?x:Math.pow(x,nf);var n=ni+nf;return(((i+1)*(n+1)-n/x)*xi-i*nf/x)*xf-pf;};return f;}
function bond_default_price(face,interest,m,n,recover,rf,dp)
{var i;var dc;var z;var zn;i=interest/m;dc=1+rf/m;z=(1-dp)/dc;zn=Math.pow(z,n);return face*((i*z+recover*dp)*(1-zn)/(1-z)+zn);}
function bond_default_prob(price,face,interest,m,n,recover,rf)
{var z;var z0;var a4;var a3;var a2;var a1;var d;var dc;var i;i=interest/m;dc=1+rf/m;d=price/face;a1=1-recover*dc+i;a2=recover-1;a3=recover*dc-i-d;a4=d-recover;z0=-a4/a3;z=newt_root(z0,zpoly(a1,a2,a3,a4,n),dzpoly(a1,a2,a3,a4,n),1.0e-4);return 1-dc*z;}
function zpoly(a1,a2,a3,a4,n)
{return function(z){return(a1*z+a2)*Math.pow(z,n)+a3*z+a4;};}
function dzpoly(a1,a2,a3,a4,n)
{return function(z){return((n+1)*a1*z+n*a2)*Math.pow(z,n-1)+a3;};}
function newt_root(x,f,fp,tol)
{var x0;for(x0=x;Math.abs(f(x0))>tol;x0-=f(x0)/fp(x0));return x0;}