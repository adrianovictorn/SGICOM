<script lang="ts">
    import { postApi } from "$lib/api";
	import { auth } from "$lib/auth";
    import brasao from "$lib/images/brasao.png"

    let usernameInput = $state('')
    let passwordInput = $state('')
    let error = $state('')

    async function entrar(event: SubmitEvent) {

        const payload = {
            username: usernameInput,
            password: passwordInput
        }
        event.preventDefault();
        error = '';
        try{
            const {token} = await postApi("/auth/login", payload);
            auth.setToken(token);

            window.location.href = '/home';
        }catch (e){
             console.error('LOGIN ERROR:', e);
             error = 'Login inválido';
        }
    }
</script>


<div class="bg-[rgb(59,130,246)] hover:bg-[rgb(37,99,235)]h-screen transition-colors duration-300">
    
    <main class="flex items-center min-h-screen  justify-center  ">
        
        <form onsubmit={entrar} class="w-full max-w-md space-y-4 grid grid-cols-1 gap-3 bg-indigo-300 shadow-2xl p-10 rounded-lg ">
            <div class="flex flex-col items-center text-center gap-2">
                <img src={brasao} alt="">
                <h1 class="text-xl font-extrabold text-gray-900 ">SGICOM</h1>
            </div>
            <p class="text-sm text-center">  Sistema de Gestão Industrial & Comercial Municipal</p>
            <div class="relative grid grid-cols-1 py-2">
               
                <label for="cpf" class="font-semibold text-gray-700 text-sm mb-2">CPF</label>
                 <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class=" absolute left-2 top-14  -translate-y-1/2 w-5 h-5 text-gray-500">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
                </svg>

                <input type="text" name="" id="cpf" bind:value={usernameInput}  placeholder="Digite seu CPF..." class="rounded bg-indigo-100 border-gray-300 shadow-2xl pl-9  ">
            </div>
            <div class=" relative grid grid-cols-1">
                <label for="senha" class="font-semibold text-gray-700 text-sm mb-2">Senha</label>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="absolute left-2 top-12 -translate-y-1/2 w-5 h-5 text-gray-500">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z" />
                </svg>

                <input type="password" name="" id="senha" bind:value={passwordInput} placeholder="Digite sua senha..." class="rounded bg-indigo-100 border-gray-300 shadow pl-9">
            </div>
            <div class="grid grid-cols-1 bg-indigo-950 text-white rounded-lg p-2">
                <button>Entrar</button>
                
            </div>
            <div class="grid grid-cols-1 text-center"><a href="#" class="text-indigo-800 ">Esqueci minha senha</a></div>
            {#if error}
                <p>{error}</p>
            {/if}
        </form>

    </main>
</div>

