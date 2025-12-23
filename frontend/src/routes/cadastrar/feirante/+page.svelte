<script lang="ts">
	import { getApi, postApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    interface CategoriaSimpleViewDTO{
        id: number
        categoria: string
    }

    interface CidadeSimpleViewDTO{
        id: number
        nome: string
    }

    type FeiranteCreateDTO = {
        matricula: string;
        banca: string;
        box: boolean;
        categoriaId: string;

        pessoaEndereco: {
            pessoaCreateDTO:{
                nome: string;
                cpf: string ;
                dataDeNascimento: string;
                telefone: string;
            };
            enderecoCreateDTO: {
                nomeLocal: string;
                numero: string;
                cep: string;
                logradouroEnum: string;
                cidadeId: string;
            };
        };
    }

    const emptyForm = (): FeiranteCreateDTO => ({
         matricula: "",
        banca: "",
        box: false,
        categoriaId: "",

        pessoaEndereco: {
            pessoaCreateDTO: {
                nome: "",
                cpf: "",
                dataDeNascimento: "",
                telefone: ""
            },

            enderecoCreateDTO: {
                nomeLocal: "",
                numero: "",
                cep: "",
                logradouroEnum: "",
                cidadeId: ""
            }
        }
    })
    
    let novaCategoria = $state("")
    
    let categoria = $state<CategoriaSimpleViewDTO[]>([])
    let cidade = $state<CidadeSimpleViewDTO[]>([]) 
    let form = $state<FeiranteCreateDTO> ({
        matricula: "",
        banca: "",
        box: false,
        categoriaId: "",

        pessoaEndereco: {
            pessoaCreateDTO: {
                nome: "",
                cpf: "",
                dataDeNascimento: "",
                telefone: ""
            },

            enderecoCreateDTO: {
                nomeLocal: "",
                numero: "",
                cep: "",
                logradouroEnum: "",
                cidadeId: ""
            }
        }

    })
    async function buscarCategoria() {
        try {
            const res = await getApi("/api/categoria/empresa/listar")
            categoria = res
        } catch (error) {
            alert("Erro ao buscar categorias")
            console.log(error)
            if(error === 403){
                alert("Você não tem permissão para acessar essa página, faça o LOGIN novamente!")
            }
        }
    }

    async function buscarCidade() {
        try {
            const res = await getApi("/api/cidade/listar")
            cidade = res
        } catch (error) {
            alert("Erro ao buscar dados de cidade !")            
        }
    }

    async function cadastrarFeirante(e: SubmitEvent) {
        e.preventDefault();

        const payload = {
            matricula: form.matricula,
            banca: form.banca,
            box: form.box,
            categoriaId: Number(form.categoriaId),

            pessoaEndereco: {
                pessoaCreateDTO: {
                    nome: form.pessoaEndereco.pessoaCreateDTO.nome,
                    cpf: form.pessoaEndereco.pessoaCreateDTO.cpf,
                    dataDeNascimento: form.pessoaEndereco.pessoaCreateDTO.dataDeNascimento,
                    telefone: form.pessoaEndereco.pessoaCreateDTO.telefone
                },

                enderecoCreateDTO: {
                    nomeLocal: form.pessoaEndereco.enderecoCreateDTO.nomeLocal,
                    numero: form.pessoaEndereco.enderecoCreateDTO.numero,
                    cep: form.pessoaEndereco.enderecoCreateDTO.cep,
                    logradouroEnum: form.pessoaEndereco.enderecoCreateDTO.logradouroEnum,
                    cidadeId: Number(form.pessoaEndereco.enderecoCreateDTO.cidadeId)
                }
            }

        }
        try {
            const res = postApi(`/api/feirante/cadastrar`,payload)
            form = emptyForm();
            alert("Cadastrado com sucesso !")
        } catch (error) {
            alert("Erro ao cadastrar")
        }
    }

    onMount(()=>{
        buscarCategoria(),
        buscarCidade()
    })
</script>

<Content>
    <section>
        <div class="bg-white rounded-lg ">
            <form action="" class="p-5 grid grid-cols-1 " onsubmit={cadastrarFeirante}>

                
                <h2>Feira</h2>
                
                <div class="grid grid-cols-2 gap-3 px-2">
                    <div class="flex flex-col">
                        <label for="">Matricula <span class="text-red-500">*</span></label>
                        <input type="text" bind:value={form.matricula}>
                    </div>
                    
                    <div class="flex flex-col">
                        <label for="">Nome do Estabelecimento <span class="text-red-500">*</span></label>
                        <input type="text" bind:value={form.banca}>
                    </div>
                </div>
                <div class="grid grid-cols-[0.8fr_auto]">
                    <div class="flex flex-col px-2">
                        <label for="">Categoria <span class="text-red-500">*</span></label>
                        <select name="" id="" bind:value={form.categoriaId}>
                            <option value="" disabled>Selecione...</option>
                            {#each categoria as ca}
                                <option value={ca.id}>{ca.categoria}</option>
                            {/each}
                            
                        </select>
                    </div>
                    <div class="flex flex-col">
                        <label for="">Box <span class="text-red-500">*</span></label>
                        <select name="" id="" bind:value={form.box}>
                            <option value={true}>Sim</option>
                            <option value={false}>Não</option>
                        </select>
                    </div>
                </div>

                <h2>Pessoa</h2>

                <div class="flex flex-col p-2">
                    <label for="">Nome <span class="text-red-500">*</span></label>
                    <input type="text" bind:value={form.pessoaEndereco.pessoaCreateDTO.nome}>
                </div>

                <div class="grid grid-cols-3 p-2 gap-2">
                    <div class="flex flex-col ">
                        <label for="">Data de Nascimento <span class="text-red-500">*</span></label>
                        <input type="date" name="" id="" bind:value={form.pessoaEndereco.pessoaCreateDTO.dataDeNascimento}>
                    </div>
                    <div class="flex flex-col">
                        <label for="">CPF <span class="text-red-500">*</span></label>
                        <input type="text" name="" id="" bind:value={form.pessoaEndereco.pessoaCreateDTO.cpf}>
                    </div>
                    <div class="flex flex-col ">
                        <label for="">Telefone <span class="text-red-500">*</span></label>
                        <input type="text" name="" id="" bind:value={form.pessoaEndereco.pessoaCreateDTO.telefone}>
                    </div>
                </div>
                <h2>
                    Endereço
                </h2>

                <div class="flex flex-col">
                    <label for="">CEP <span class="text-red-500">*</span></label>
                    <input type="text" name="" id="" bind:value={form.pessoaEndereco.enderecoCreateDTO.cep}>
                </div>

                <div class="grid grid-cols-4 gap-2">
                    <div class="flex flex-col">
                        <label for="">Logradouro <span class="text-red-500">*</span></label>
                        <select name="" id="" bind:value={form.pessoaEndereco.enderecoCreateDTO.logradouroEnum}>
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
                        <label for="">Endereço <span class="text-red-500">*</span></label>
                        <input type="text" bind:value={form.pessoaEndereco.enderecoCreateDTO.nomeLocal} >
                    </div>
                    <div class="flex flex-col">
                        <label for="">Número <span class="text-red-500">*</span></label>
                        <input type="text" name="" id="" bind:value={form.pessoaEndereco.enderecoCreateDTO.numero} >
                    </div>
                    <div class="flex flex-col">
                        <label for="">Ponto de Referência</label>
                        <input type="text" name="" id=""  >
                    </div>
                </div>
                <div class="flex flex-col">
                    <label for="">Cidade <span class="text-red-500">*</span></label>
                    <select name="" id="" bind:value={form.pessoaEndereco.enderecoCreateDTO.cidadeId}>
                        <option value="" >Selecione...</option>
                        {#each cidade as cid }
                            <option value={cid.id}>{cid.nome}</option>
                        {/each}
                    </select>
                </div>

                <div class="flex flex-col mt-5">
                    <button type="submit" class="text-white font-semibold bg-[#3AAB30] w-full cursor-pointer hover:bg-[#316b3a] rounded-md py-2" >Cadastrar</button>
                </div> 
            </form>
            
        </div>

        
    </section>
</Content>