<script lang="ts">
	import { page } from "$app/state";
import { getApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    
    type formType = {
        matricula: string
        banca: string 
        categoria: string
        box: boolean | null
        page: number
        size: number
        totalPages: number
    }
    
    type PageItem = number;

    const blockSize = 5

  

   
    interface FeiranteViewDTO {
        id: number
        responsavel: string
        matricula: string
        banca: string
        haveBox: boolean
        categoria: string
        
    }

    let formulario = $state<formType>({
        matricula: "",
        banca: "",
        categoria: "",
        box: null,
        page: 0,
        size: 10,
        totalPages: 0
    })

    let FeiranteState = $state<FeiranteViewDTO[]> ([]);
    let paginasDoBloco = $derived.by(() => blocosPorPagina(formulario.page, formulario.totalPages));
   async function buscarMultiplosCampos() {
        const params = new URLSearchParams();

        let matricula = formulario.matricula?.trim();
        let banca = formulario.banca?.trim();
        if(matricula){ params.append("termo", matricula)}
        if(banca){ params.append("termo", banca)}

        params.append("size", String(formulario.size))
        params.append("page", String(formulario.page))
        try {
            const res = await getApi(`/api/feirante/busca/multiplos/campos?${params.toString()}`)
            FeiranteState = res.content
            formulario.totalPages = res.totalPages;
        } catch (error) {
            
        }
   }

    onMount(() => {
        buscarMultiplosCampos()
    })
                            //48
    function blocosPorPagina(page: number, totalPages: number): PageItem[] {
        const last = totalPages - 1 

        const start = Math.floor(page / blockSize)* blockSize
        const end = Math.min(start + blockSize - 1, last)

        return Array.from({ length: end - start + 1}, (_,i) => start + i);
    }

    function irPara(p: number){
        const last = Math.max(0, formulario.totalPages - 1)
        formulario.page = Math.max(0, Math.min(last, p))
        buscarMultiplosCampos()
    }

    function proximaPagina(){
        const last = formulario.totalPages - 1

        if(formulario.page < last){
            formulario.page +=1
            buscarMultiplosCampos()

        }
        
    }

    function paginaAnterior(){
        if(formulario.page > 0){
            formulario.page -= 1
            buscarMultiplosCampos()
        }
    }
</script>
<Content>
    <section class="bg-white p-5 rounded-lg">
        <form class="grid grid-cols-3 gap-3" onsubmit={() =>buscarMultiplosCampos()}>
            <div class="flex flex-col">
                <label for="">Matricula</label>
                <input type="text" placeholder="Matricula" bind:value={formulario.matricula}>
            </div>

            <div class="flex flex-col">
                <label for="">Banca</label>
                <input type="text" placeholder="Banca" bind:value={formulario.banca}>
            </div>

            <div class="flex flex-col">
                <label for="">Categoria</label>
                <input type="text" placeholder="Categoria" bind:value={formulario.categoria} >
            </div>

            <div class="bg-blue-600 text-white rounded-lg p-2 flex flex-col col-span-3">
                <button type="submit">Consultar</button>
            </div>
        </form>
    </section>

    <section class="bg-white p-5 rounded-lg mt-5">
        <div>
            <table class="flex flex-col">
                <thead class=" bg-[#0f2037] rounded p-3 text-white font-normal">
                    <tr class="grid grid-cols-6">
                        <th class="px-2">Matrícula</th>
                        <th>Banca</th>
                        <th>Responsável</th>
                        <th>Possui Box ?</th>
                        <th>Categoria</th>
                        <th>Ações</th>
                    </tr>
                </thead>

                <tbody class="flex flex-col">
                    {#each FeiranteState as feirante }
                    <tr class="grid grid-cols-6 text-center border-b  items-center odd:bg-gray-200 border-gray-300">
                            <td class="">{feirante.matricula}</td>
                            <td>{feirante.banca}</td>   
                            <td>{feirante.responsavel}</td> 
                            <td>{feirante.haveBox}</td>
                            <td>{feirante.categoria}</td>
                            <td>
                                <div>
                                    <button>Inativar</button>
                                </div>

                                <div>
                                    <button>Detalhes</button>
                                </div>
                            </td>
                        </tr>
                    {/each}
                </tbody>

                <tfoot class="flex flex-col text-white">
                    <tr class="bg-[#0f2037]">
                        <td class="flex gap-4 text-center justify-center">
                            <div>
                                <button type="button" class="cursor-pointer" hidden={formulario.page === 0} onclick={paginaAnterior} disabled={formulario.page === 0}>{"<"}</button>
                            </div>
                                <div>{#each paginasDoBloco as paginas}
                                    <button type="button" onclick={() => irPara(paginas)}>{paginas + 1}</button>
                                {/each}</div>
                            <div>
                                <button type="button" onclick={() => proximaPagina()} hidden={formulario.page+1 >= formulario.totalPages} class="cursor-pointer">{">"}</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </section>
</Content>