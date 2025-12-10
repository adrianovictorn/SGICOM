<script lang="ts">
	import { postApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";

    type UsuarioCreateDTO = {
        cpf: string
        nome: string
        senha: string
        funcao: string
    }

    let cpf = $state("")
    let nome = $state("")
    let senha = $state("")
    let senhaConfirmacao = $state("")
    let funcao = $state("")

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
        } catch (error) {
            alert("Erro ao Cadastrar novo Usuário");   
        }
    }
</script>

<Content>
    <section>
        
        <form action="" onsubmit={() => cadastrarUsuario()} class="bg-white rounded-lg p-7">
            <div class="flex flex-col ">
                <label for="cpf">CPF <span class="text-red-500">*</span></label> 
                <input type="text" bind:value={cpf} required placeholder="O CPF será utilizado como forma de identificação...">
            </div>

            <div class="flex flex-col gap-1">
                <label for="nome">Nome <span class="text-red-500">*</span></label>
                <input type="text" name="" id="" bind:value={nome} required placeholder="Nome do Usuário">
            </div>
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

            <div class="flex flex-col mt-4 bg-green-400 hover:bg-green-700 cursor-pointer p-2 rounded-lg text-white">
                <button type="submit">Cadastrar</button>
            </div>
        </form>
    </section>
</Content>