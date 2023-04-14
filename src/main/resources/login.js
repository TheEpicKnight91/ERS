window.onload = async function()
{
    let response = await fetch(`${domain}/session`);
    let responseBody = await response.json();

    if (responseBody.success)
    {
        window.location = "./dashboard"
    }
}
/* function that runs when the page loads */
document.getElementById("Loginbtn").addEventListener("click", async function (event)
{
    //this is to stop the form from reloading 
    event.preventDefault();
    
    //retrieve input elements from the dom
    let usernameInputElem = document.getElementById("username");
    let passwordInputElem = document.getElementById("password");

    //get values from the input elements and put it into an object
    let user = {
        username: usernameInputElem.value,
        password: passwordInputElem.value
    };

    //send the http request
    let response = await fetch(`${domain}/session`, {
        method: "POST",
        body: JSON.stringify(user)
    });

    //retrieve the response body
    let responseBody = await response.json();


    //logic after response body
    if(responseBody.success == false)
    {
        let messageElem = document.getElementById("message");
        messageElem.innerText = responseBody.message;
    }else
    {
        window.location = `./dashboard?userId=${responseBody.data.id}&status=${"STATUS"}`;
    }
});

document.getElementById("Registerbtn").addEventListener("click", async function(event)
{
    event.preventDefault();
    window.location = `./register`;
});