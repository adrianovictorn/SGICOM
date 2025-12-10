<script lang="ts">
	import { page } from "$app/state";
	import { getApi, putApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";
	import { get } from "svelte/store";

    interface Cidade{
        id: number
        nome: string
    }


    interface EnderecoSimpleViewDTO{
        id: number
        nomeLocal: string
        numero: string
        cep: string
        logradouroEnum: string
        pontoReferencia: string
        cidade: Cidade
    }

    
    interface TipoEmpresaViewDTO{
        id: number
        tipo: string
    }

    interface CategoriaEmpresaViewDTO{
        id: number
        categoria: string
    }

    interface Cnae {
        id: number
        codigo: string
        descricao: string
    }

    interface EmpresaViewDTO{
        id: number
        razaoSocial: string
        cnae: Cnae[]
        cnpj: string
        ativo: boolean
        email: string
        atividade: string
        numeroColaboradores: number
        enderecos: EnderecoSimpleViewDTO[]
        tipoEmpresa: TipoEmpresaViewDTO
        categoriaEmpresa: CategoriaEmpresaViewDTO

    }

    type EnderecoForm ={
        id:number
        nomeLocal: string
        cep: string 
        numero: string 
        cidadeId: number
        pontoReferencia: string
        logradouroEnum: string
    }

    let enderecoForms = $state<EnderecoForm[]>([])
    let modoEdicaoEmpresa = $state(false)
    let enderecoSelecionado = $state<number|null>(null)
    let cadastrarLocal = $state("")
    let cadastrarNumero = $state("")
    let cadastrarCidadeId = $state<number| null> ()
    let cadastrarCep = $state("")
    let cadastrarPontoDeReferencia = $state("")
    let cadastrarLogradouro = $state(null)
    let tipoEmpresaId = $state<number | null> (null)
    let categoriaEmpresaId = $state<number | null> (null)
    let atualizarRazaoSocial = $state("")
    let atualizarCNPJ = $state("")
    let atualizarAtivo = $state<boolean>(false)
    let atualizarEmail = $state("")
    let atualizarAtividade = $state("")
    let atualizarNumeroColaboradores = $state<number|null>(0)
    let atualizarCategoria = $state("")
    let atualizarCnaeTexto  = $state("")
        
    let atualizarCNAEId = $state<number[]>([])
    let atualizarEnderecosId = $state<number[]> ([])
    
    let empresa = $state<EmpresaViewDTO| null> (null)
    let tipoEmpresa = $state<TipoEmpresaViewDTO[]>([])
    let categoriaEmpresa = $state<CategoriaEmpresaViewDTO[]>([])


    async function atualizarEndereco(endereco: EnderecoForm){
        const payload ={
            nomeLocal: endereco.nomeLocal,
            numero: endereco.numero,
            cep: endereco.cep,
            pontoReferencia: endereco.pontoReferencia,
            logradouroEnum: endereco.logradouroEnum

            
        }
        try {
            console.log(payload)
            const res = await putApi(`/api/endereco/atualizar/${endereco.id}`,payload)
            alert("Endereço atualizado com sucesso !")
            modoEdicaoEmpresa = false
            
        } catch (error) {
            
        }
    }


    async function atualizarEmpresa(id: number) {
        const payload = {
            empresaUpdateDTO: {
                razaoSocial: atualizarRazaoSocial,
                cnpj: atualizarCNPJ,
                cnae: atualizarCNAEId,
                ativo: atualizarAtivo,
                email: atualizarEmail,
                atividade: atualizarAtividade,
                numeroColaboradores: atualizarNumeroColaboradores,
                enderecosIds: atualizarEnderecosId,
                
                
                tipoEmpresa: tipoEmpresaId,
                categoriaEmpresa: categoriaEmpresaId
            },
            enderecoCreateDTO: {
                nomeLocal: cadastrarLocal,
                numero: cadastrarNumero,
                cep: cadastrarCep,
                pontoReferencia: cadastrarPontoDeReferencia,
                logradouroEnum: cadastrarLogradouro,
                cidadeId: cadastrarCidadeId
            }
            
            
            
        } 
        console.log(`Atualizar endereço: ${atualizarEnderecosId}` )
        try{
            console.log(payload)
            const res = await putApi(`/api/empresa/atualizar/${id}`,payload)
            buscarCategoriaEmpresa()
            buscarEmpresaPorId(id)
            alert("Empresa Atualizada com Sucesso !")
            modoEdicaoEmpresa = false
        }catch(error){
            console.log(error)
        }
    }
    
    //Buscar Empresa
    async function buscarEmpresaPorId(id: number) {
        try{
            const res = await getApi(`/api/empresa/buscar/${id}`)
            empresa =  res
            
            atualizarCNPJ = res.cnpj
            atualizarRazaoSocial = res.razaoSocial
            atualizarCNAEId = (res.cnae as Cnae[]).map((cn) => cn.id)
            atualizarCnaeTexto = (res.cnae as Cnae[]).map((cn) => `${cn.codigo} - ${cn.descricao}`).join(',')
            atualizarEmail = res.email
            atualizarCategoria = res.categoriaEmpresa.categoria
            atualizarEnderecosId = (res.enderecos as EnderecoSimpleViewDTO[]).map((end) =>  end.id)
            atualizarNumeroColaboradores = res.numeroColaboradores
            atualizarAtividade = res.atividade
            atualizarAtivo = res.ativo
            tipoEmpresaId = res.tipoEmpresa.id
            categoriaEmpresaId = res.categoriaEmpresa.id 

            enderecoForms = (res.enderecos as EnderecoSimpleViewDTO[]).map((end) => ({
                id: end.id,
                nomeLocal: end.nomeLocal,
                numero: end.numero,
                cep: end.cep,
                cidadeId: end.cidade.id,
                pontoReferencia: end.pontoReferencia,
                logradouroEnum: end.logradouroEnum
        }))
            
        }catch(error){
            console.log(error)
        }
    }

    //Buscar TipoEmpresa
    async function buscarTipoEmpresa() {
        try {
            const res = await getApi("/api/tipo/empresa/listar")
            tipoEmpresa = res
        } catch (error) {
            
            console.log(`CONSULTA INVALIDA ${error}`)
        }
    }

    //Buscar CategoriaEmpresa
    async function  buscarCategoriaEmpresa() {
        try {
            const res = await getApi("/api/categoria/empresa/listar")
            categoriaEmpresa = res
        } catch (error) {
            console.log(error)
        }
    }

    onMount( ()=> {

        const idParam = page.params.id
        const id= Number(idParam);

        if(!isNaN(id)){
            buscarEmpresaPorId(id)
            buscarTipoEmpresa()
            buscarCategoriaEmpresa()
        } else{
            console.error("ID de empresa inválido a rota", idParam)
        }
    })

    function editar(){
        modoEdicaoEmpresa = !modoEdicaoEmpresa
    }

    function selecionarEndereco(id: number){
        const endereco = enderecoForms.find(e => e.id === id) ?? null
        if(endereco === null){
            alert("Endereço não encontrado !")
            return
        }
        atualizarEndereco(endereco);
    }

   

</script>
<Content>
    <main class="bg-white p-5 rounded-lg">
        <section  class="grid grid-cols-1 gap-2">
            <div class="flex">
                <h2 class="text-[#0F6D30] font-bold text-xl">Empresa</h2>
                <div class="ml-auto  border rounded-md p-2   hover:bg-[#1C1C05]  hover:text-white shadow-2xl ">
                    <button class="flex cursor-pointer hover:text-white" onclick={editar}>
                        <svg class="w-6 h-6 text-gray-800  hover:text-white dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m4.988 19.012 5.41-5.41m2.366-6.424 4.058 4.058-2.03 5.41L5.3 20 4 18.701l3.355-9.494 5.41-2.029Zm4.626 4.625L12.197 6.61 14.807 4 20 9.194l-2.61 2.61Z"/>
                        </svg>
                        Editar
                    </button>
                </div>
            </div>
            <div class="flex flex-col">
                <label for="">Nome da Empresa</label>
                <input type="text" bind:value={atualizarRazaoSocial} disabled={!modoEdicaoEmpresa} class:bg-gray-200={!modoEdicaoEmpresa}>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-2">
                    <div class="flex flex-col">
                        <label for="">Cadastro Nacional da Pessoa Jurídica (CNPJ)</label>
                        <input type="text"  disabled={!modoEdicaoEmpresa} bind:value={atualizarCNPJ} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                    </div>
                    <div class="flex flex-col">
                        <label for="">Classificação Nacional de Atividades Econômicas </label>                            
                        <input type="text" bind:value={atualizarCnaeTexto} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                    </div>

                    <div class="flex flex-col">
                        <label for="">Situação Cadastral</label>

                          {#if modoEdicaoEmpresa}
                                <select name="" id="" disabled={!modoEdicaoEmpresa} bind:value={atualizarAtivo} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                                    {#if empresa?.ativo === true}
                                        <option value={true}>Ativo</option>
                                        <option value={false}>Desativado</option>

                                        {:else}
                                        <option value={false}>Desativado</option>
                                        <option value={true}>Ativado</option>
                                    {/if}

                                </select>

                                {:else}
                                {#if empresa?.ativo}
                                    <select name="" id="" disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                                        <option value={true}>Ativo</option>
                                    </select>
                                    {:else}
                                    <select name="" id="" disabled={!modoEdicaoEmpresa}  class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                                        <option value={false}>Desativado</option>
                                    </select>
                                {/if}
                         {/if}
                    </div>
                </div>
        </section>

        <section class="grid grid-cols-1 gap-2 mt-5">
            <h2 class="text-[#0F6D30] font-bold text-xl">Dados da Empresa</h2>
            <div class="flex flex-col">
                <label for="">Email</label>
                <input type="text" bind:value={atualizarEmail} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
            </div>

            <div class="flex flex-col">
                <label for="">Número de Colaboradores</label>
                <input type="number" bind:value={atualizarNumeroColaboradores} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-2">
                <div class="flex flex-col">
                    <label for="">Ramo de Atuação da Empresa</label>
                    <select name="" id="" disabled={!modoEdicaoEmpresa} class:bg-gray-200={!modoEdicaoEmpresa} bind:value={categoriaEmpresaId}>
                        {#each categoriaEmpresa as categoria }
                            <option value={categoria.id}>{categoria.categoria}</option>
                        {/each}
                    </select>
                </div>
                <div class="flex flex-col">
                    <label for="">Tipo de Empresa</label>
                    <select name="" id="" bind:value={tipoEmpresaId} disabled={!modoEdicaoEmpresa} class:bg-gray-200={!modoEdicaoEmpresa}>
                        {#each tipoEmpresa as tipo}
                                <option value={tipo.id}>{tipo.tipo}</option>
                        {/each}
                    </select>
                </div>
            </div>


            <div class="flex flex-col">
                <label for="">Atividade Desempenhada Pela Gestão</label>
                <input type="text" name="" id="" bind:value={atualizarAtividade} disabled={!modoEdicaoEmpresa} class:bg-gray-200={!modoEdicaoEmpresa}>
            </div>
        </section>

        {#if modoEdicaoEmpresa && empresa}
            <div class="flex flex-col">
                <button onclick={() => atualizarEmpresa(empresa!.id)} class="bg-[#0F6D30] rounded p-2 text-white font-bold cursor-pointer mt-2 hover:bg-[#073317]">Atualizar</button>
            </div> 
        {/if}

        

        <section class="grid grid-cols-1 gap-2 mt-5">
            <h2 class="text-[#0F6D30] font-bold text-xl">Endereço da Empresa</h2>
            {#each enderecoForms as end, i}

            
            
            <p class="text-[#0f2037] text-center mt-5 font-semibold">Endereço {i + 1}</p>
            <div class="flex flex-col">
                <label for="">Cidade</label>
                <input type="text" name="" id="" bind:value={end.cidadeId} disabled={!modoEdicaoEmpresa}  class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
            </div>
            <div class="grid grid-cols-2 gap-2">
                <div class="flex flex-col">
                    <label for="">Logradouro</label>
                    <input type="text" bind:value={end.logradouroEnum} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                </div>
                <div class="flex flex-col">
                    <label for="">Endereço</label>
                    <input type="text" bind:value={end.nomeLocal} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                </div>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-2">
                <div class="flex flex-col">
                    <label for="">CEP</label>
                    <input type="text" bind:value={end.cep} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                </div>
                <div class="flex flex-col">
                    <label for="">Número</label>
                    <input type="text" bind:value={end.numero} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                </div>
                <div class="flex flex-col">
                    <label for="">Ponto de Referência</label>
                    <input type="text" bind:value={end.pontoReferencia} disabled={!modoEdicaoEmpresa} class="bg-gray-200" class:bg-gray-200={!modoEdicaoEmpresa}>
                </div>
            </div>
            
            <button class="rounded bg-[#e9e429] hover:[#565410] p-2 text-white font-bold cursor-pointer hover:bg-[#565410]" onclick={() => selecionarEndereco(end.id)} hidden={!modoEdicaoEmpresa}>Editar</button>
            {/each}
        </section>
    </main>
</Content>