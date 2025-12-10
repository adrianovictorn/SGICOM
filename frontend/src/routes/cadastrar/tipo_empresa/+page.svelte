<script lang="ts">
	import { deleteApi, getApi, postApi, putApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    interface TipoEmpresa{ 
        id: number
        tipo: string
    }

    let novoTipo = $state('');
    let modoEdicao = $state(false)
    let tipoEmpresa = $state<TipoEmpresa[]> ([])
    let tipoEmpresaSelecionado = $state<TipoEmpresa |null> (null)

    async function cadastrarTipoEmpresa(event: SubmitEvent){
        event.preventDefault();
        const payload = { 
            tipo: novoTipo
        }

        try {
            await postApi("/api/tipo/empresa/cadastrar",payload)
            limparCampos()
            listarTipoEmpresa()
        } catch (error) {
            console.log(error)
        }
    }

    async function listarTipoEmpresa(){
        try {
            const res = await getApi("/api/tipo/empresa/listar")
            tipoEmpresa = res
        } catch (error) {
            console.log(error)   
        }
    }

    async function atualizarTipoEmpresa(event: Event){
        const confirmou  = confirm("Deseja atualizar essa instância ? ")

        if(!confirmou){
            return;
        }

        const payload = {
            tipo: novoTipo
        }
        try {
            const res = await putApi(`/api/tipo/empresa/atualizar/${tipoEmpresaSelecionado?.id}`, payload)
            alert("Tipo atualizado com sucesso !")
            limparCampos()
            listarTipoEmpresa()

            modoEdicao = false
        } catch (error) {
         console.log("ERROR", error)   
        }
    }

    async function deletarTipoEmpresa(id : number) {
        const confirmou = confirm("Deseja Realmente Apagar Esse Registro ?")

        if(!confirmou){
            return;
        }
        try {
            const res = await deleteApi(`/api/tipo/empresa/deletar/${id}`)
            alert("Tipo deletado com Sucesso !")
            listarTipoEmpresa()
        } catch (error) {
            
            const validador = tipoEmpresa.find(tipo => tipo.id === id)
            
            if(validador != null){
                alert("Há empresas vinculadas a esse tipo de empresa, por favor, delete primeiro a empresa para apagar o tipo! ")
                return;
            }
            alert("Erro ao se conectar ao servidor !")
            console.log(error)
        }
        
    }

    function abrirCampos(id: number){
        const tipo = tipoEmpresa.find(tip => tip.id === id)
        if(!tipo){
            return ;
        }

        tipoEmpresaSelecionado = tipo
        novoTipo = tipo.tipo

        modoEdicao = true
    }

    function cancelarAtualizacao(){
        modoEdicao = false
    }

    function limparCampos(){
        novoTipo = ''
    }
    onMount(() => {
        listarTipoEmpresa()
    })
</script>

<Content>
        <section>
            <form action="" class="grid grid-cols-1 md:grid-cols-2 bg-white p-10 rounded-lg " onsubmit={cadastrarTipoEmpresa}>
                <div class="flex flex-col p-2">
                    <label for="" class="mb-2 font-extrabold text-4xl text-center md:text-start">Tipo de Empresa <span class="text-red-500">*</span></label>
                    <input type="text" class="w-full rounded-lg border-gray-400 p-2" placeholder="Adicionar tipo de empresa" bind:value={novoTipo} required>
                </div>
                {#if  !modoEdicao}
                    <div class="flex flex-col mt-8 p-2">
                        <button type="submit" class="text-white font-semibold bg-[#3AAB30] w-full cursor-pointer hover:bg-[#316b3a] rounded-md py-2">Cadastrar</button>
                    </div>
                    

                    {:else}

                        <div>
                            <div class="flex flex-col mt-8 p-2">
                                <button type="button" onclick={atualizarTipoEmpresa} class="text-white font-semibold bg-[#6193D8] w-full cursor-pointer hover:bg-[#203047] rounded-md py-2">Salvar</button>
                            </div>
                            <div class="flex flex-col p-2 " >
                                <button type="button" onclick={cancelarAtualizacao} class="rounded-md border text-center py-2 bg-red-600 hover:bg-red-800 text-white cursor-pointer">Cancelar Edição</button>
                            </div>
                        </div>

                {/if}
            </form>
        </section>

        <section>
            <div class="mt-5 bg-white rounded-lg md:p-4">
                {#each tipoEmpresa as tipo}
                    <div class="grid grid-cols-[70px_1fr_auto] gap-1 px-2 md:px-0">
                        <div class="flex flex-col">
                            <label for="">Id</label>
                            <input type="text" value={tipo.id} disabled = { !modoEdicao ||!tipoEmpresaSelecionado || tipoEmpresaSelecionado.id !== tipo.id}>
                        </div>

                        <div class="flex flex-col">
                            <label for="">Nome</label>
                            <input type="text" value={tipo.tipo} disabled = { !modoEdicao ||!tipoEmpresaSelecionado || tipoEmpresaSelecionado.id !== tipo.id}>
                        </div>

                        <div class="flex flex-col justify-center  text-center">
                            <label for="">Ações</label>

                            <div class="border rounded-lg px-3 py-1">
                                <button aria-label="Abrir edição" onclick={() => abrirCampos(tipo.id)}>
                                    <svg class="w-6 h-6 text-[#928e12] dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                        <path fill-rule="evenodd" d="M14 4.182A4.136 4.136 0 0 1 16.9 3c1.087 0 2.13.425 2.899 1.182A4.01 4.01 0 0 1 21 7.037c0 1.068-.43 2.092-1.194 2.849L18.5 11.214l-5.8-5.71 1.287-1.31.012-.012Zm-2.717 2.763L6.186 12.13l2.175 2.141 5.063-5.218-2.141-2.108Zm-6.25 6.886-1.98 5.849a.992.992 0 0 0 .245 1.026 1.03 1.03 0 0 0 1.043.242L10.282 19l-5.25-5.168Zm6.954 4.01 5.096-5.186-2.218-2.183-5.063 5.218 2.185 2.15Z" clip-rule="evenodd"/>
                                    </svg>
                                </button>
                                <button aria-label="Deletar" type="button" onclick={() => deletarTipoEmpresa(tipo.id)}>
                                    <svg class="w-6 h-6 text-[#504848] dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                        <path fill-rule="evenodd" d="M8.586 2.586A2 2 0 0 1 10 2h4a2 2 0 0 1 2 2v2h3a1 1 0 1 1 0 2v12a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V8a1 1 0 0 1 0-2h3V4a2 2 0 0 1 .586-1.414ZM10 6h4V4h-4v2Zm1 4a1 1 0 1 0-2 0v8a1 1 0 1 0 2 0v-8Zm4 0a1 1 0 1 0-2 0v8a1 1 0 1 0 2 0v-8Z" clip-rule="evenodd"/>
                                    </svg>
                                </button>
                            </div>


                        </div>
                    </div>
                {/each}
            </div>
        </section>
</Content>
 