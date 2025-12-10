<script lang="ts">
	import { goto } from "$app/navigation";
	import { getApi, patchApi, putApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    interface Empresa {
        id: number
        razaoSocial: string
        cnae: Cnae[]
        cnpj: string
        ativo: boolean
    }

    interface CategoriaEmpresa{
        id: number
        categoria: string

    }

    interface Cnae {
        id: number
        codigo: string
        descricao: string
    }

    type PageItem = number | "dots";

    let cnpj = $state("")
    let cnae = $state<Cnae[]>([])
    let razaoSocial = $state("")
    let cidadeId: number | null = null;
    let categoriaId = $state("")
    let categoriaEmpresa = $state<CategoriaEmpresa[]> ([])
    let error = $state("")

    let termo = $state("")
    let page = $state(0);
    const size = 10;

    let empresas = $state<Empresa[]> ([]);
    let totalPages = $state(0); 

    async function buscarCategoria() {

        try {
            const res = await getApi("/api/categoria/empresa/listar")
            categoriaEmpresa = res
        } catch (error) {
            alert(`Erro ao receber dados, ${error}`)
        }
        
    }

    async function desativarEmpresa(id: number) {
        try{
            const res = await patchApi(`/api/empresa/desativar/${id}`)
            alert("Empresa Desativada com Sucesso !")
            buscarEmpresas()

            
        }catch(error){
             console.log(error)
        }
    }

    async function buscarEmpresas() {


        const params = new URLSearchParams();

        if(cnpj) params.append("cnpj", cnpj);
        if(razaoSocial) params.append("razaoSocial", razaoSocial);
        if(cidadeId) params.append("cidadeId", String(cidadeId));
        if(categoriaId) params.append("categoria", categoriaId);

        params.append("page", String(page))
        params.append("size", String(size))

        try {
           
            const res = await getApi(`/api/empresa/consultar?${params.toString()}`)
            console.log(res)
            empresas = res.content ?? [];
            totalPages = res.totalPages ?? 0;
        } catch (error) {
            alert(error)   
        }
    }

    function computePages(totalPages: number, page: number): PageItem[]{
        if(totalPages <=1) return [];

         if(totalPages <= 5){
            return Array.from({length: totalPages}, (_, i) => i)
        }

        const items: PageItem[] = [];

        const last = totalPages - 1;

        items.push(0)

        const windowSize = 1;
    

        let start = Math.max(1, page - windowSize)
        let end = Math.min(last - 1, page + windowSize)

        if(start > 1){
            items.push("dots")
        }

        for(let p = start; p<=end; p++){
            items.push(p);
        }


        if(end < last - 1){
            items.push("dots")
        }

        items.push(last)


        return items;
    }

    const pages = $derived(computePages(totalPages, page))

    function   irParaPagina(p:number){
        if(p<0 || p>= totalPages) return;
            page = p;
            buscarEmpresas();
    }

    function proximaPagina(){
        if(page < totalPages - 1){
            page += 1;
            buscarEmpresas();
            console.log(page)
        }
    }

    function irParaEdicao(id: number){
        goto(`/dados/empresa/${id}`)
    }
    
    function paginaAnterior(){
        if(page > 0){
            page -=1;
            buscarEmpresas();
        }
    }

    onMount(() => {
        buscarCategoria()
        buscarEmpresas()
    })
</script>

<Content> 
    <section class="bg-white rounded-lg">
        <form action="" >
           
            <div class=" grid grid-cols-1 md:grid-cols-3 p-5">
                <div class="flex flex-col p-2">
                    <label for="">Razão Social</label>
                    <input type="text" bind:value={razaoSocial} placeholder="Digite o nome da empresa">
                </div>
                <div class="flex flex-col p-2">
                    <label for="">CNPJ</label>
                    <input type="text" name="" id="" placeholder="Digite o número do CNPJ" bind:value={cnpj}>
                </div>
                
                <div class="flex flex-col p-2">
                    <label for="">Categoria</label>
                    <select name="" id="" bind:value={categoriaId}>
                        <option value="">Selecione</option>
                        {#each categoriaEmpresa as cat}
                        <option value={String(cat.id)}>{cat.categoria}</option>
                        {/each}
                    </select>
                </div>
            </div>


            <div class="grid grid-cols-1 mt-2 p-2 w-full r">
                <div class="flex flex-col"><button class="text-white font-semibold bg-[#3AAB30] w-full cursor-pointer hover:bg-[#316b3a] rounded-md py-2" onclick={buscarEmpresas} type="button">Buscar</button></div>
            </div>
        </form>
    </section>

    <section class="bg-white grid grid-cols-1 p-5 mt-5 rounded-lg">
        <div class="rounded-lg flex flex-col bg-[#232a33] py-4 text-white  text-center relative overflow-x-auto">
            <table class="min-w-[600px] text-xs md:text-sm w-full ">
                <thead>
                    <tr class=" ">
                        <th class="px-3 py-2 font-bold w-100">Razão Social</th>
                        <th class="px-3 py-2 font-bold">CNPJ</th>
                        <th class="px-3 py-2 font-bold">CNAE</th>
                        <th class="px-3 py-2 font-bold">Status</th>
                        <th colspan="2" class="px-3 py-2 font-bold">Ações</th>
                    </tr>
                </thead>
            
            
                <tbody class="   ">
                        {#each empresas as emp }
                            <tr class="bg-neutral-100 odd:bg-neutral-200 text-black border border-b-2 border-neutral-300  ">
                                <td colspan="1" class="px-1 py-4 ">{emp.razaoSocial}</td>
                                <td>{emp.cnpj}</td>
                                <td>
                                    {#each emp.cnae as  cnaes}
                                        {cnaes.codigo}
                                    {/each}
                                </td>
                                {#if  emp.ativo === true}
                                    <td>Ativo</td>
                                    {:else}
                                    <td>Desativado</td>
                                {/if}

                                <td>
                                    <button aria-label="editar" onclick={() => irParaEdicao(emp.id)}>
                                        <svg class="w-6 h-6 text-[#5582B5] dark:text-[#A4A202] " aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                            <path stroke="currentColor" stroke-width="2" d="M21 12c0 1.2-4.03 6-9 6s-9-4.8-9-6c0-1.2 4.03-6 9-6s9 4.8 9 6Z"/>
                                            <path stroke="currentColor" stroke-width="2" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"/>
                                        </svg>
                                    </button>

                                    <button type="button" aria-label="desativar" onclick={() => desativarEmpresa(emp.id)} class="cursor-pointer">
                                        <svg class="w-6 h-6 text-red-800 dark:text-white "  aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                        <path fill-rule="evenodd" d="M4 4a2 2 0 1 0 0 4h16a2 2 0 1 0 0-4H4Zm0 6h16v8a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2v-8Zm10.707 5.707a1 1 0 0 0-1.414-1.414l-.293.293V12a1 1 0 1 0-2 0v2.586l-.293-.293a1 1 0 0 0-1.414 1.414l2 2a1 1 0 0 0 1.414 0l2-2Z" clip-rule="evenodd"/>
                                        </svg>

                                    </button>
                                </td>
                            </tr>
                        {/each}
                    </tbody>
            
                <tfoot>
            
                    {#if totalPages > 1}
                    <tr>
                        <td colspan="6" class="text-center p-4 cursor-pointer text-sm">
                            <button type="button" onclick={paginaAnterior} disabled={page === 0} aria-label="Página anterior" class="m-1  hover:underline">
                                &lt;
                            </button>
                            {#each pages as p }
                                {#if p === "dots"}
                                    <span class="m-1  hover:underline">...</span>
                                    {:else}
                                    <button type="button" class="m-1  hover:underline" onclick={()=> irParaPagina(p)}>
                                        {p+1}
                                    </button>
                                {/if}
                            {/each}
                            <button type="button" onclick={proximaPagina} class="m-1 hover:underline">
                                &gt;
                            </button>
                        </td>
                    </tr>
                    {/if}
                </tfoot>
            </table>
        </div>
        
    </section>
</Content>  