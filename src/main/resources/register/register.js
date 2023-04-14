/* function that runs when the page loads */
document.getElementById("Registerbtn").addEventListener("click", async function (event)
{
    //this is to stop the form from reloading 
    event.preventDefault();
    
    //retrieve input elements from the dom
    let messageElem = document.getElementById("message");
    let usernameInputElem = document.getElementById("username");
    let passwordInputElem = document.getElementById("password");
    let firstnameInputElem = document.getElementById("firstname");
    let lastnameInputElem = document.getElementById("lastname");
    let emailInputElem = document.getElementById("email");
    let roleInputElem = document.querySelectorAll('input[name="employeerole"]');

    
    if (usernameInputElem.value == "" || passwordInputElem.value == "" || firstnameInputElem.value == "" || lastnameInputElem.value == "" || emailInputElem.value == "")
    {
        messageElem.innerText = "Please fill out all the fields above";
    }
    else
    {
        let role;
        let selected = false;
        for(let radio of roleInputElem)
        {
            if (radio.checked)
            {
                role = radio.value;
                selected = true;
                break;
            }
        }
        if (selected == false)
        {
            messageElem.innerText = "Please selected one of the roles";
        }
        else
        {
            console.log(usernameInputElem.value);
            console.log(role);
            //get values from the input elements and put it into an object
            let user = {
                username : usernameInputElem.value,
                password : passwordInputElem.value,
                first_name : firstnameInputElem.value,
                last_name: lastnameInputElem.value,
                email : emailInputElem.value,
                role : role
            };
    
            //send the http request
            let response = await fetch(`${domain}/register`, {
                method: "POST",
                body: JSON.stringify(user)
            });
    
            //retrieve the response body
            let responseBody = await response.json();
    
    
            //logic after response body
            if(responseBody.success == false)
            {
                messageElem.innerText = responseBody.message;
            }else{
                //redirect page to login page if credentials were successful
                window.location = `../`;
            }
        }
    }
});

document.getElementById("Cancelbtn").addEventListener("click", async function(event)
{
    event.preventDefault();
    window.location = `../`;
});