<script lang="ts">
	import { getApi, postApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    type OrigemBusca = "codigo" | "descricao";
    
    interface TipoEmpresa {
        id: number
        tipo: string
    }

    interface Cnae {
        id: number
        codigo: string
        descricao: string
    }
    interface CategoriaEmpresa{
        id: number
        categoria: string
    }

     interface Cidade{
        id: number
        nome: string
        codigoIbge: string
    }

    type ErrosForm = {
        razaoSocial: string
        cnae: string
        cnpj: string
        email: string
        numeroColaboradores: string
        atividade: string
        cep: string
        cidadeId: string
    }

    let erros = $state<ErrosForm>({
        razaoSocial: "",
        cnae: "",
        cnpj: "",
        email: "",
        numeroColaboradores: "",
        atividade: "",
        cep: "",
        cidadeId: ""
    })

    let tipoEmpresa = $state<TipoEmpresa[]>([])
    let categoriaEmpresa = $state<CategoriaEmpresa[]>([])
    let listaCidades = $state<Cidade[]>([])
    let cnae = $state<Cnae[]>([])

    let novoRazaoSocial = $state('');
    let novoCnae = $state('');
    let novoCnpj = $state('');
    let novoEmail = $state('');
    let novoAtividade = $state("Acompanhamento");
    let novoNumeroColaboradores = $state<number> (0)
    let novoTipoEmpresa = $state('')
    let novoCategoriaEmpresa = $state('')
    let novoLocal = $state('')
    let novoNumero = $state('')
    let novoCep = $state('')
    let novoPontoReferencia = $state('')
    let novoLogradouro = $state('')
    let novoCidadeId = $state('')
    let termo = $state("")
    let carregando = $state(false)
    let comboBoxAberto = $state(false)
    let origemBusca = $state<OrigemBusca> ("codigo");
    let descricao = $state("")
    let cnaeCodigo = $state("")
    let selecionarCnaeId = $state<number[]> ()
    async function listarTipo() {
        try{
            const res = await getApi("/api/tipo/empresa/listar")

            tipoEmpresa = res
        }catch(error){

        }
    }

    async function buscarCnae(texto: string) {

        const params = new URLSearchParams({
            termo: texto,
            page: "0",
            size: "10"
        })
        try {
            const res = await getApi(`/api/cnae/consultar?${params.toString()}`)
            const lista = Array.isArray(res) ? res : (res?.content ?? []);
            cnae = lista
        } catch (error) {
            cnae = [];
        }
    }

    async function listarCategoria() {
        try {
            const res = await getApi("/api/categoria/empresa/listar")

            categoriaEmpresa = res
        } catch (error) {
            
        }
    }

    async function listarCidades(){
        try{
            const res = await getApi("/api/cidade/listar")
            listaCidades = res
        }catch(error){

        }
    }

    async function cadastrarEmpresa(event: SubmitEvent){
        event.preventDefault()

        if(!validarCampos()){
            alert("Verifique os campos obrigatórios")
            return;
        }
        const payload = {
            empresaCreateDTO: {
                razaoSocial: novoRazaoSocial,
                cnae: selecionarCnaeId,
                cnpj: novoCnpj,
                email: novoEmail,
                atividade: novoAtividade,
                numeroColaboradores: novoNumeroColaboradores,
                tipoEmpresa: novoTipoEmpresa ? Number(novoTipoEmpresa) : null,
                categoriaEmpresa: novoCategoriaEmpresa ? Number(novoCategoriaEmpresa) : null
            },
            enderecoCreateDTO: {
                nomeLocal: novoLocal,
                numero: novoNumero,
                cep: novoCep,
                pontoReferencia: novoPontoReferencia,
                logradouroEnum: novoLogradouro,
                cidadeId: novoCidadeId ? Number(novoCidadeId) : null   
            }
        }
        try{
            const res = await postApi("/api/empresa/cadastrar", payload)

           alert("Nova empresa cadastrada com sucesso !")
            limparCampos()
            limparErros()
        } catch(error){
            console.log(error)
        }
    }

   onMount(() => {
    listarTipo()
    listarCategoria()
    listarCidades()
   })


   function limparCampos(){
        novoRazaoSocial = ""
        novoCnpj = ""
        novoEmail = ""
        novoAtividade = ""
        novoNumeroColaboradores = 0
        novoTipoEmpresa = ""
        novoCategoriaEmpresa = ""
        novoLocal = ""
        novoNumero = ""
        novoCep = ""
        novoPontoReferencia = ""
        novoLogradouro = ""
        novoCidadeId = ""
        cnaeCodigo = ""
        descricao = ""
   }

   function limparErros(){
    erros ={ 
        razaoSocial: "",
        cnae: "",
        cnpj: "",
        email: "",
        numeroColaboradores: "",
        atividade: "",
        cep: "",
        cidadeId: ""
    }
   }

   function validarCampos() : boolean{
        limparErros()

        let valido = true;

        if(!novoRazaoSocial.trim()){
            erros.razaoSocial = "Informe a razão social"
            valido = false
        }

        return valido;
   }

   function somenteNumeros(valor: string): string{
    return valor.replace(/\D/g, "");
   }

   function formatarCNAE(valor: string): string{
    let digitos = somenteNumeros(valor).slice(0,7)

    let formatado = "";

    if(digitos.length > 0){
        formatado = digitos.substring(0,4)
    }

    if(digitos.length >= 5){
        formatado += "-" + digitos.substring(4,5)
    }

    if(digitos.length >= 6){
        formatado += "/" + digitos.substring(5,7)
    }

    return formatado
   }

   function formatarCNPJ(valor: string): string{
        let digitos = somenteNumeros(valor).slice(0, 14)

        let formatado = "";

        if(digitos.length > 0){
            formatado = digitos.substring(0,2)
        }

        if(digitos.length >= 3){
            formatado += "." + digitos.substring(2,5)
        }

        if(digitos.length >= 6){
            formatado += "." + digitos.substring(5,8)
        }

        if(digitos.length >= 9){
            formatado += "/" + digitos.substring(8,12)
        }

        if(digitos.length >= 13){
            formatado += "-" + digitos.substring(12,14)
        }

        return formatado
   }

   function selecionarCnae(cnae: Cnae){
    selecionarCnaeId = [cnae.id];
    cnaeCodigo = cnae.codigo
    descricao = cnae.descricao
    termo = cnae.codigo
    comboBoxAberto = false
   }
</script>


<svelte:head>
    <title>Cadastrar Empresa</title>
</svelte:head>


<Content>
    <form onsubmit={cadastrarEmpresa} class="bg-white grid grid-cols-1 rounded-xl px-10 py-5">
        <h2 class="p-3 text-[#106F31] text-shadow-2xs font-bold text-2xl ">Empresa</h2>
        <div class="flex flex-col p-2">
            <label for="" class="text-[#94AECA] font-bold text-md">Razão Social <span class="text-red-500">*</span></label>
            <input type="text" bind:value={novoRazaoSocial} class="border-[#073216] rounded-lg p-3" placeholder="Nome da Empresa" required>

            {#if erros.razaoSocial}
                <span class="text-red-500 text-sm mt-1">{erros.razaoSocial}</span>
            {/if}
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-3 p-2">
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">CNAE <span class="text-red-500">*</span></label>
                <input type="text" bind:value={cnaeCodigo} class="border-[#073216] rounded-lg" placeholder="Classificação Nacional de Atividades" autocomplete="off" required 
                onfocus={() => {
                    comboBoxAberto = true
                    if(cnae.length === 0){
                        buscarCnae("")
                    }
                }}
                oninput={
                    (e) => {
                        const input = e.currentTarget as HTMLInputElement;
                        const valorDigitado = input.value

                        origemBusca = "codigo"
                        selecionarCnaeId =[]
                        
                        cnaeCodigo  = formatarCNAE(valorDigitado)
                        termo = valorDigitado;
                        comboBoxAberto = true;

                        buscarCnae(termo);
                    }
                }
                
                onblur={() =>{
                    setTimeout(() => (comboBoxAberto = false), 150)
                }}
                > 

                {#if comboBoxAberto}
                    <div class="absolute z-20  mt-17 bg-gray-200 rounded-lg shadow-lg max-h-60 overflow-y-auto text-sm w-[69.5vw] border border-gray-200 ">
                        {#if carregando}
                            Carregando...
                            {:else}
                            
                            <ul>
                                {#each cnae as  cnaes}
                                    <li class="mt-2 ml-2 mb-2">
                                        <button type="button"  onmousedown={() => selecionarCnae(cnaes)}>
                                            <span class="text-[0.9rem]">  Código: {cnaes.codigo}  -  Descrição: {cnaes.descricao}</span> 
                                        </button>
                                    </li>
                                {/each}
                            </ul>
                        {/if}
                    </div>
                {/if}
            </div>
            <div class="flex flex-col md:col-span-2">
                <label for="" class="text-[#94AECA] font-bold text-md ">Descrição <span class="text-red-500">*</span></label>
                <input type="text" bind:value={descricao} class="border-[#073216] rounded-lg text-sm py-2.5" placeholder="Classificação Nacional de Atividades" autocomplete="off"
                 oninput={(e) => {
                    const valor = (e.currentTarget as HTMLInputElement).value;

                    origemBusca = "descricao"
                    selecionarCnaeId =[]
                    termo = valor
                    comboBoxAberto = true
                    
                    buscarCnae(termo)
                }}>
            </div>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-3 p-2">
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">CNPJ <span class="text-red-500">*</span></label>
                <input type="text" bind:value={novoCnpj}  class="border-[#073216] rounded-lg" placeholder="Cadastro Nacional de Pessoa Jurídica" required
                oninput={
                    (e) => {
                        const target = e.target as HTMLInputElement;
                        novoCnpj = formatarCNPJ(target.value)
                    }
                }
                >
            </div>
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">Email <span class="text-red-500">*</span></label>
                <input type="text" bind:value={novoEmail}  class="border-[#073216] rounded-lg" placeholder="E-mail da Empresa" required>
            </div>
            <div class="flex flex-col" >
                <label for="" class="text-[#94AECA] font-bold text-md">Número de Colaboradores <span class="text-red-500">*</span></label>
                <input type="number" bind:value={novoNumeroColaboradores}  class="border-[#073216] rounded-lg" required>
            </div>
        </div>

        <div class="flex flex-col p-2">
            <label for="" class="text-[#94AECA] font-bold text-md">Atividade <span class="text-red-500">*</span></label>
            <input type="text" bind:value={novoAtividade}  class="border-[#073216] rounded-lg md:p-3 bg-gray-200" placeholder="Atividade a ser Executada pela Gestão" disabled required>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 p-2 gap-4">
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">Categoria <span class="text-red-500">*</span></label>
                <select name="" id="" bind:value={novoCategoriaEmpresa}  class="border-[#073216] rounded-lg" required>
                    <option value="">Selecione...</option>
                    {#each categoriaEmpresa as categoria }
            
                        <option value={categoria.id}>{categoria.categoria}</option>
                    {/each}
                </select>
            </div>
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">Tipo <span class="text-red-500">*</span></label>
                <select name="" id="" bind:value={novoTipoEmpresa}  class="border-[#073216] rounded-lg" required>
                <option value="">Selecione...</option>
                {#each tipoEmpresa as  tip}
                    <option value={tip.id}>{tip.tipo}</option>
                {/each}
                </select>
            </div>
        </div>

        <h2 class="p-3 text-[#0F6D30] text-shadow-2xs font-bold text-xl">Endereço</h2>
          <div class="flex flex-col p-2">
                <label for="" class="text-[#94AECA] font-bold text-md">Cep <span class="text-red-500">*</span></label> 
                <input type="text" bind:value={novoCep}   class="border-[#073216] rounded-lg p-3" placeholder="XXXXX-XXX" required>
            </div>
        <div class="grid grid-cols-1 md:grid-cols-4 lg:grid-cols-4 gap-3 p-2">
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">Logradouro <span class="text-red-500">*</span></label>
                <select name="" id="" bind:value={novoLogradouro}   class="border-[#073216] rounded-lg">
                    <option value="">Selecione...</option>
                    <option value="RUA">Rua</option>
                    <option value="FAZENDA">Fazenda</option>
                    <option value="SITIO">Sitio</option>
                    <option value="CHACARA">Chacara</option>
                    <option value="AVENIDA">Avenida</option>
                    <option value="TRAVESSA">Travessa</option>
                    <option value="ALAMEDA">Alameda</option>
                    <option value="ESTRADA">Estrada</option>
                    <option value="RODOVIA">Rodovia</option>
                    <option value="VIA">Via</option>
                    <option value="COLONIA">Colonia</option>
                    <option value="DISTRITO_INDUSTRIAL">Distrituto Industrial</option>
                </select>
            </div>
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">Local <span class="text-red-500">*</span></label>
                <input type="text" bind:value={novoLocal}   class="border-[#073216] rounded-lg" placeholder="Digite o nome da Rua, Avenida etc..." required>
            </div>
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">Número <span class="text-red-500">*</span></label>
                <input type="text" bind:value={novoNumero}  class="border-[#073216] rounded-lg" placeholder="Digite o número ou S/N" required>
            </div>
          
            <div class="flex flex-col">
                <label for="" class="text-[#94AECA] font-bold text-md">Ponto de Referência</label>
                <input type="text" bind:value={novoPontoReferencia}   class="border-[#073216] rounded-lg" placeholder="Exemplo: Próximo a Escola ">
            </div>

        </div>
        
        <div class="flex flex-col mb-4 p-2">
            <label for="" class="text-[#94AECA] font-bold text-md">Cidade <span class="text-red-500">*</span></label>
            <select name="" id="" bind:value={novoCidadeId}   class="border-[#073216] rounded-lg" required>
            <option value="">Selecione...</option>

                {#each listaCidades as cid }
                    <option value={cid.id}>{cid.nome}</option>
                {/each} 
            </select>
        </div>

        <div class="flex flex-col mb-5">
            <button type="submit" class="text-white font-semibold bg-[#3AAB30] w-full cursor-pointer hover:bg-[#316b3a] rounded-md py-2">Cadastrar</button>
        </div> 

    </form>

</Content>
   
