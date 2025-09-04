console.log('wishlist.js open');

const wishlistFind = async() => { console.log('wishlistFind. exe')
    try{
    const option = { method :"GET"}
    const response = await fetch("/member/wishlist", option);
    const data = await response.json();
    const wishlistBox = document.querySelector('#wishlistBox')

    let html = '';
    for(let i = 0; i<data.length; i++){
        const wishlist = data[i];
        html += `${wishlist.fname}`
    }
    wishlistBox.innerHTML = html;
    }catch(error){
        console.log(error);
        // location.href = "/member/login.jsp";
    };

} 
wishlistFind();



