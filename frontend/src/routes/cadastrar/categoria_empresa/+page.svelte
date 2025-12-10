<script lang="ts">
	import { deleteApi, getApi, postApi, putApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";


    interface CategoriaEmpresa {
        id: number
        categoria: string
    }

    let novaCategoria = $state('');
    let categoriaEmpresa = $state<CategoriaEmpresa[]>([])
    let categoriaSelecionada = $state<CategoriaEmpresa | null> (null);
    let modoEdicao = $state(false);


    async function cadastrarCategoria(event: SubmitEvent) {
        event.preventDefault();
        const payload = {
            categoria: novaCategoria
        }
        try{
            const res = await postApi("/api/categoria/empresa/cadastrar", payload)
            alert("Nova categoria de Empresa adicionada !")
            limparCampos()
            carregarCategoria()

        }catch (error){
            console.log(error)
        }
    }

    async function carregarCategoria(){
        try {
            const res = await getApi("/api/categoria/empresa/listar")

            categoriaEmpresa =  res
        } catch (error) {
            alert("Erro ao se conectar ao servidor !")
        }
    }

    async function atualizarCategoria(event: Event) {
        try {
            const payload = {
                categoria: novaCategoria
            }
            const res = await putApi(`/api/categoria/empresa/atualizar/${categoriaSelecionada?.id}`, payload)
            limparCampos()
            carregarCategoria()
            modoEdicao = false
        } catch (error) {
            
        }
        
    }

    async function deletarCategoria(id: number){
        const confirmou = confirm("Deseja realmente deletar essa categoria ?")

        if(!confirmou){
            return;
        }
        try{
            const res = await deleteApi(`/api/categoria/empresa/deletar/${id}`)
            alert("Categoria deletada com sucesso !")
            carregarCategoria()
        }catch(error){
            
            console.log(error)
        }
    }

    function limparCampos(){
        novaCategoria = '';
    }

    function abrirCampos(id: number){
        const categoria = categoriaEmpresa.find(cat => cat.id === id)
        if(!categoria)return;

        categoriaSelecionada = categoria;
        novaCategoria = categoria.categoria;  

        modoEdicao = true
    }

    function cancelarAtualizacao(){
        limparCampos()
        modoEdicao = false
    }

    onMount(() => {
        carregarCategoria()
    })
</script>

<svelte:head>
    <title>Configurar Categoria</title>
</svelte:head>

<Content>

    <section>
        <form onsubmit = {cadastrarCategoria} class="grid grid-cols-1 md:grid-cols-2 bg-white p-10 rounded-lg">
            <div class="flex flex-col p-2">
                <label for="" class="mb-2 font-extrabold text-4xl text-center md:text-start" >Categoria <span class="text-red-500">*</span></label>
                <input type="text" name="" id="" bind:value={novaCategoria} placeholder="Adicione uma nova categoria">
            </div>
        
           

            {#if modoEdicao}
                <div class="grid grid-cols-1">
                    <div class="flex flex-col p-2">
                        <button type="button" onclick={atualizarCategoria} class="text-white font-semibold bg-[#6193D8] w-full cursor-pointer hover:bg-[#203047] rounded-md py-2">Salvar</button>
                    
                    </div>
                    <div class="flex flex-col p-2 " >
                        <button type="button" onclick={cancelarAtualizacao} class="rounded-md border text-center py-2 bg-red-600 hover:bg-red-800 text-white cursor-pointer">Cancelar Edição</button>
                    </div>
                </div>

                {:else}
                 <div class="flex flex-col mt-7 p-2">
                    <button type="submit"  class="text-white font-semibold bg-[#3AAB30] w-full cursor-pointer hover:bg-[#316b3a] rounded-md py-2">Cadastrar</button>
                </div>
            {/if}
        </form>
    </section>


    <section>
        <div class="mt-5 bg-white rounded-lg md:rounded-lg md:p-2 ">
            {#each  categoriaEmpresa as  cat, i}
                <div class="grid grid-cols-[70px_1fr_auto] md:grid-cols-[150px_0.9fr_auto] items-center  p-2"> 
                    
                    <div class="flex flex-col text-start md:text-start items-baseline-last">
                        <label for="" class="ml-2">Id:</label>
                        <input type="text" value={cat.id} class="w-15 md:w-30 text-center py-3 " disabled={!modoEdicao}>
                    </div>

                    <div class="flex flex-col px-2 ">
                        <label for="">Nome:</label>
                        <input value={cat.categoria} class=" py-3" disabled={!modoEdicao || !categoriaSelecionada || categoriaSelecionada.id !== cat.id}>
                    </div>

                    <div class=" flex flex-col text-center md:w-30">
                        <label for="">Ações:</label>
                        <div class="w-15 md:w-30 py-3 border rounded-lg flex flex-row justify-center gap-1">
                            <button type="button" onclick={() => abrirCampos(cat.id)} aria-label="Abrir campos para edição">
                                <svg class="w-6 h-6  text-gray-800 dark:text-white  " aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                <path fill-rule="evenodd" d="M14 4.182A4.136 4.136 0 0 1 16.9 3c1.087 0 2.13.425 2.899 1.182A4.01 4.01 0 0 1 21 7.037c0 1.068-.43 2.092-1.194 2.849L18.5 11.214l-5.8-5.71 1.287-1.31.012-.012Zm-2.717 2.763L6.186 12.13l2.175 2.141 5.063-5.218-2.141-2.108Zm-6.25 6.886-1.98 5.849a.992.992 0 0 0 .245 1.026 1.03 1.03 0 0 0 1.043.242L10.282 19l-5.25-5.168Zm6.954 4.01 5.096-5.186-2.218-2.183-5.063 5.218 2.185 2.15Z" clip-rule="evenodd"/>
                                </svg>
                            </button>
                            <button aria-label="Deletar categoria" onclick={() => deletarCategoria(cat.id)}>
                                <svg class="w-6 h-6 text-gray-800  dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18 17.94 6M18 18 6.06 6"/>
                                </svg>
                            </button>

                        </div>
                    </div>
                </div>


            {/each}
        </div>
    </section>
</Content>