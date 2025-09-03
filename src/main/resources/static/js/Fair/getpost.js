const params = new URL(location.href).searchParams;
const fno = params.get('fno');

//상세정보
const getPost = async () =>{
    const response = await fetch(`/fair/getpost?fno=${fno}`);
    const data = await response.json();

    document.querySelector('.fname').innerHTML=data.fname;
    document.querySelector('.fcount').innerHTML=data.fcount;
    document.querySelector('.fimg').innerHTML = data.fimg;
//document.querySelector('.fimg').innerHTML = `<img src="${URL.createObjectURL(file)}" class="uploaded-img" />`;
    document.querySelector('.fprice').innerHTML=data.fprice;
    document.querySelector('.furl').innerHTML=data.furl;
    document.querySelector('.start_date').innerHTML=data.start_date;
    document.querySelector('.end_date').innerHTML=data.end_date;
    document.querySelector('.finfo').innerHTML=data.finfo;
}//func end
getPost();