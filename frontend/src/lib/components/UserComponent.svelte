
<script lang="ts">
	import { goto } from "$app/navigation";
	import { auth } from "$lib/auth";
	import { onMount } from "svelte";

    type JwtPayload = {
        nome: string
        roles: string[]
    }

    let payload: JwtPayload | null = null;
    let primeiraLetra = '?';
    let formatarRole = '?';

    onMount(() =>{
        payload = auth.getPayload() as JwtPayload | null;
        const letra = payload?.nome
        const role = payload?.roles ?? []
        console.log(payload)
        if (letra){
            primeiraLetra = letra.trim().charAt(0).toUpperCase();
        } 
        if(role?.includes("ROLE_ADMIN")){
            formatarRole = "Administrador"
        }

        
    })

    let abrirOptions = false;
    
    function logout(){
        auth.logout();
        alert("Logout realizado com sucesso !")
        goto("/login")
    }
    function abrirMenu(){
        abrirOptions = !abrirOptions
    }

</script>

<ul>       
    {#if payload !== null}
        <div class="p-1 bg-[#1F4372] flex gap-1 rounded-l-full rounded-r-full items-center justify-center border-2 border-transparent" class:border-white={abrirOptions} >
            <div class="relative inline-flex items-center justify-center w-10 h-10 overflow-hidden bg-neutral-300 rounded-full">
                <span class="font-medium text-lg dark:text-black text-black ">{primeiraLetra}</span>
            </div>
            <div class=" flex">
                <span class="text-sm font-medium text-white  rounded  ">{payload?.nome}</span>
            
            </div>
            <div>
            </div>
            <button onclick={abrirMenu} aria-label="abrir" class="flex items-center justify-center">
                    <svg class="w-5 h-5  text-white dark:text-white mr-3  transform transition-normal duration-300 {abrirOptions ? 'rotate-180': ''}"  aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 9-7 7-7-7"/>
                    </svg>
                
            </button>
        </div>
        {#if abrirOptions}
        <div class="text-[14px] absolute bg-gray-200 dark:bg-gray-800 rounded-lg flex flex-col ml-3 mt-1 w-40 border p-3 gap-1  border-gray-800 ">
            <div class="flex flex-col text-center mb-2">
                <span class="text-[12px]">Logado como:</span>
                <span class="text-[12px] font-bold"> {payload?.nome}</span>
                <span class="text-[12px] font-bold mt-2 text-[#239447]"> {formatarRole}</span>

                <span></span>
            </div>
            <hr class="text-gray-600 my-1">
            <div class="grid grid-cols-1">
                <li class="flex gap-1  items-center">
                    <svg class="w-6 h-6  text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                        <path stroke="currentColor" stroke-width="2" d="M7 17v1a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1v-1a3 3 0 0 0-3-3h-4a3 3 0 0 0-3 3Zm8-9a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"/>
                    </svg>
                    <a href="">Meu Perfil</a>
                </li>
                <li class="flex items-center gap-1">
                    <svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 13v-2a1 1 0 0 0-1-1h-.757l-.707-1.707.535-.536a1 1 0 0 0 0-1.414l-1.414-1.414a1 1 0 0 0-1.414 0l-.536.535L14 4.757V4a1 1 0 0 0-1-1h-2a1 1 0 0 0-1 1v.757l-1.707.707-.536-.535a1 1 0 0 0-1.414 0L4.929 6.343a1 1 0 0 0 0 1.414l.536.536L4.757 10H4a1 1 0 0 0-1 1v2a1 1 0 0 0 1 1h.757l.707 1.707-.535.536a1 1 0 0 0 0 1.414l1.414 1.414a1 1 0 0 0 1.414 0l.536-.535 1.707.707V20a1 1 0 0 0 1 1h2a1 1 0 0 0 1-1v-.757l1.707-.708.536.536a1 1 0 0 0 1.414 0l1.414-1.414a1 1 0 0 0 0-1.414l-.535-.536.707-1.707H20a1 1 0 0 0 1-1Z"/>
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z"/>
                    </svg>
                
                    <a href="">Configurações</a>
                </li>
            </div>
            <hr class="text-gray-600 my-1">
            <div class="grid grid-cols-1">
                <li class="flex  items-center gap-1 ">
                    <svg class="w-6 h-6 text-gray-800 dark:text-white mt-3 ml-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none"  viewBox="0 0 24 24">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0z"/>
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708z"/>
                    </svg>
                    <button type="button" onclick={logout} class="cursor-pointer">Logout</button>
                </li>
            </div>
        </div>

        {/if}
    {:else}
        <div class="bg-pink-900 flex flex-col rounded p-2 max-w-[50vw] md:max-w-[20vw]">
            <p class=" text-lg ">Você está deslogado ! Faça o Login para ter acesso ao módulos</p>
            <a href="/login" class="text-center text-md">Clique aqui</a>
        </div>
    {/if}
    
</ul>