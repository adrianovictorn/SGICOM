<script lang="ts">
	import { getApi, patchApi, postApi, putApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    type UsuarioCreateDTO = {
        cpf: string
        nome: string
        senha: string
        funcao: string
    }       

    interface UsuarioListDTO{
        id: number
        cpf: string
        nome: string
        senha: string
        ativo: boolean
        funcao: string 
        dataHorarioCriacao: string

    }

    let cpf = $state("")
    let nome = $state("")
    let senha = $state("")
    let ativo = $state(true)
    let senhaConfirmacao = $state("")
    let funcao = $state("")
    let modoEdicao = $state(false)

    let usuarios = $state<UsuarioListDTO[]> ([])
    let usuarioSelecionadoId = $state<number | null>(null)
    const funcoes = [
        {
        funcao: "ROLE_USER", label: "Usuário"
      },
      {
        funcao: "ROLE_ADMIN", label: "Administrador"
      }
]

    async function cadastrarUsuario() {
        const payload = {
            cpf: cpf,
            nome: nome,
            senha: senha,
            funcao: funcao
        }
        try {
            const res = postApi("/api/usuario/cadastrar", payload);
            alert("Usuário Cadastrado com Sucesso")
            limparCampos()
        } catch (error) {
            alert("Erro ao Cadastrar novo Usuário");   
        }
    }

    async function atualizarUsuario(id: number) {
        const payload = {
            nome: nome,
            senha: senha,
            ativo: ativo,
            funcao: funcao
        }
        try {
            const res = await putApi(`/api/usuario/atualizar/${id}`,payload)
            console.log(id)
            console.log(payload)
            console.log(res)
            alert("Usuário atualizado com sucesso !")
        } catch (error) {
            alert("Ero atualizar usuário ")
        }
    }


    async function listarUsuarios(){
        try {  
            const res = await getApi("/api/usuario/listar")
            usuarios = res
            
            console.log(res)
        } catch (error) {
            
        }
    }

    async function  ativarOrInativar(id: number) {
        try {
            const res = await patchApi(`/api/usuario/ativo/${id}`)
            alert("Usuário atualizado com sucesso !")
            console.log(res)
        } catch (error) {
            alert("Erro ao atualizar o usuário")
        }
    }
    
    function selecionarUsuario(usuario: UsuarioListDTO){
        usuarioSelecionadoId = usuario.id
        console.log("Usuario ID", usuario.id)
        console.log("ID:",usuarioSelecionadoId)
        modoEdicao = true

        nome = usuario.nome
        cpf = usuario.cpf
        senha = usuario.senha
        funcao = usuario.funcao
        ativo = usuario.ativo   
    }

    function atualizarUsuarioSelecionadoId(){
        if(usuarioSelecionadoId === null){
          return ;  
        } 
    
        atualizarUsuario(usuarioSelecionadoId)
    }


    function somenteNumeros(valor: string): string{
        return valor.replace(/\D/g, "")
    }

    function formatarCPF(valor: string): string{
        const digitos = somenteNumeros(valor).slice(0,11)

        let formatado = ""

        if(digitos.length > 0){
            formatado = digitos.substring(0,3)
        }

        if(digitos.length >= 4){
            formatado += "." + digitos.substring(3,6)
        }

        if(digitos.length >= 7){
            formatado += "." + digitos.substring(6,9)
        }

        if(digitos.length >=10){
            formatado += "-" + digitos.substring(9,11)
        }

        return formatado
    }

    function limparCampos(){
        cpf = "",
        nome = "",
        senha = "",
        funcao = ""
    }

    onMount(() => {
        listarUsuarios()
    })
</script>


<svelte:head>
    <title>Cadastrar Usuário</title>
</svelte:head>


<Content>
    <section class="grid grid-cols-1 md:grid-cols-[0.6fr_1fr] gap-5">
        
        <div class="">
            <form action="" onsubmit={() => cadastrarUsuario()} class="bg-white rounded-lg p-7 flex flex-col gap-3">
                <h2 class="">Cadastro de Usuário</h2>
                <div class="flex flex-col ">
                    <label for="cpf">CPF <span class="text-red-500">*</span></label>
                    <input type="text" bind:value={cpf} required placeholder="XXX.XXX.XXX-XX"
                     oninput={(e)=> {
                        const input = e.currentTarget as HTMLInputElement
                        const valor = input.value
                        cpf = formatarCPF(valor)
                    }}>
                </div>
                <div class="flex flex-col gap-1">
                    <label for="nome">Nome <span class="text-red-500">*</span></label>
                    <input type="text" name="" id="" bind:value={nome} required placeholder="Nome do Usuário">
                </div>
                {#if !modoEdicao}
                    <div class="flex flex-col gap-1">
                        <label for="ativo">Ativo <span class="text-red-500">*</span></label>
                        <select name="" id="" bind:value={ativo}>
                            <option value={true}>Ativo</option>
                            <option value={false}>Inativo</option>
                        </select>
                    </div>
                {/if}
                <div class="flex flex-col gap-1">
                    <label for="senha">Senha <span class="text-red-500">*</span></label>
                    <input type="password" name="" id="" bind:value={senha} required placeholder="Senha de Acesso ao Sistema">
                </div>
                <div class="flex flex-col gap-1">
                    <label for="confirmacao">Confirmar Senha <span class="text-red-500">*</span></label>
                    <input type="password" bind:value={senhaConfirmacao} class=" border-green-700" required placeholder="Confirmação de Senha">
                    {#if senhaConfirmacao !== senha}
                     <span>As senhas são diferentes, digite novamente</span>
                    {/if}
                </div>
                <div class="flex flex-col gap-1">
                    <label for="">Função <span class="text-red-500">*</span></label>
                    <select name="" id=""  bind:value={funcao}>
                        <option value="">Selecione..</option>
                        {#each funcoes as funcao }
                            <option value={funcao.funcao}>{funcao.label}</option>
                        {/each}
                        </select>
                </div>
                {#if !modoEdicao}
                    <div class="flex flex-col mt-4 bg-green-400 hover:bg-green-700 cursor-pointer p-2 rounded-lg text-white">
                        <button type="submit">Cadastrar</button>
                    </div>
                {:else}
                    <div class="flex flex-col mt-4 bg-yellow-400 hover:bg-yellow-700 cursor-pointer p-2 rounded-lg text-white">
                        <button type="button" onclick={() => atualizarUsuarioSelecionadoId()}>Atualizar</button>
                    </div>
                {/if}
            </form>
        </div>


        <div >
            <div class="bg-white rounded-lg p-7">
                <h2>Lista de Usuários Cadastrados</h2>

                <table>
                    <thead class="bg-[#cfddfa]">
                        <tr >
                            <th class="border">Nome</th>
                            <th class="border px-5">CPF</th>
                            <th class="border px-4">Função</th>
                            <th class="border px-4">Status</th>
                            <th class="border">Data Criação</th>
                            <th class="border">Ações</th>
                        </tr>
                    </thead>

                    <tbody class="">
                        {#each usuarios as usuario }
                        <tr class="text-center odd:bg-neutral-200 ">
                            <td class="border p-3">{usuario.nome}</td>
                            <td class="border">{usuario.cpf}</td>
                            {#if usuario.funcao === "ROLE_ADMIN"}
                                <td class="border">{"Admin"}</td>
                            {/if}
                            <td class="border">{usuario.ativo}</td>
                            <td class="border">{usuario.dataHorarioCriacao}</td>
                            <td class="flex p-2 gap-2">
                                <button type="button" onclick={() => selecionarUsuario(usuario)}>
                                    Atualizar
                                </button>
                                {#if usuario.ativo === true}
                                    <button class="" onclick={() => ativarOrInativar(usuario.id)}>Inativar</button>
                                    
                                    {:else}
                                    <button class="" onclick={() => ativarOrInativar(usuario.id)}>Ativar</button>
                                {/if}
                                
                                <button>
                                    Excluir
                                </button>
                            </td>
                        </tr>
                        {/each}
                    </tbody>
                </table>
            </div>
        </div>
    </section>


</Content>