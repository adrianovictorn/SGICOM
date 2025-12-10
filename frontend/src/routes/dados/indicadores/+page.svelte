<script lang="ts">
	import { getApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    interface EmpresaCountDTO {
        totalEmpresas: number
    }

    interface CategoriaPercentualDTO{
        categoria: string
        quantidade: number
        percentual: number
    }

    interface EmpresaColaboradoresSUMDTO{
        totalColaboresGeral: number
    }

    let totalColaboradores = $state<EmpresaColaboradoresSUMDTO | null>(null)
    let totalDeEmpresas = $state<EmpresaCountDTO | null >(null)
    let categoriaPercentual = $state<CategoriaPercentualDTO[]>([])

    async function carregarCategoriaPercentual(){
        try {
            const res = await getApi("/api/empresa/porcentagem/categoria")
            categoriaPercentual = res
        } catch (error) {
            alert("Erro ao buscar categoria da empresa")
        }
    }

    async function carregarTotalColaboradores() {
        try {
            const res = await getApi("/api/empresa/contagem/colaboradores")
            totalColaboradores = res as EmpresaColaboradoresSUMDTO
        } catch (error) {
            
        }
    }
    
    async function carregarTotalEmpresas() {
        try {
            const res = await getApi("/api/empresa/contagem")
            console.log(res)
            totalDeEmpresas = res as EmpresaCountDTO

        } catch (error) {
            
        }
    }


    onMount(() => {
        carregarTotalEmpresas()
        carregarTotalColaboradores()
        carregarCategoriaPercentual()
    })
</script>


<svelte:head>
    <title>Indicadores</title>
</svelte:head>

<Content>

    <section class="bg-white rounded-lg p-5 flex flex-col text-center">
        <h2>Empresas</h2>
        <div class="text-center grid grid-cols-1  md:grid-cols-3 gap-5">

            <div class="border rounded-md py-3 shadow-2xl">
                <p>Total de Empresas Cadastradas </p>
               <p>{totalDeEmpresas?.totalEmpresas}</p>
            </div>

            <div class="border rounded-md py-3 shadow-2xl">
                <p>Total de Colaboradores Informados</p>
                <p>{totalColaboradores?.totalColaboresGeral}</p>
            </div>

            
            <div class="border rounded-lg p-5 ">
            {#each categoriaPercentual as cat}
            
                    <div class="flex items-center gap-3">
                        <span class="w-32 text-sm font-semibold text-gray-700">{cat.categoria}</span>
                        <div class="flex-1 bg-gray-200 rounded-full h-4 overflow-hidden">
                            <div class="h-4 bg-blue-600 rounded-full  transition-all" style={`width: ${cat.percentual}%;`}></div>
                        </div>

                        <span class="w-16 text-right text-sm text-gray-700">
                            {cat.percentual.toFixed(1)}%
                        </span>
                    </div>
                {/each}
            </div>

        </div>
    </section>
</Content>