<script lang="ts">
	import { getApi, postApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";
	import { onMount } from "svelte";

    type PageItem = number | "...";
    
    interface CnaeViewDTO {
        id: number
        codigo: string
        descricao: string
    }

    let codigo = $state("")
    let descricao = $state("")
    let termo = $state("")
    let page = $state(0)
    let size = $state(10)
    let totalPages = $state(0)
    let pagination = $derived.by((): PageItem[] => {
        const last = totalPages - 1;
        if(last < 1) return [];

        const a = Math.max(1, page - 1);
        const b = Math.min(last - 1, page + 1);
        
        const leftDots: PageItem[] = a > 1 ? ["..."] : [];
        const rightDots: PageItem[] = b < last - 1 ? ["..."] : [];


        return [
            0,
            ...leftDots,
            ...Array.from({ length: b - a + 1 }, (_, i) => a + i),
            ...rightDots,
            last
        ];
    })
    let loading = true

    let cnae = $state<CnaeViewDTO[]>([])


    async function cadastrarCNAE() {
        const payload ={
            codigo,
            descricao
        }
        try {
            const res = await postApi("/api/cnae/cadastrar", payload)
            alert("CNAE adicionado com sucesso !")
            codigo = ""
            descricao = ""
        } catch (error) {
            alert ("Erro ao cadastrar CNAE")
        }
    }

    async function listarCnaes() {
        try {
            const q = new URLSearchParams({
                termo: termo.trim(),
                page: String(page),
                size: String(size)
            })
            const res = await getApi(`/api/cnae/consultar?${q.toString()}`)
            cnae = res.content
            totalPages = res.totalPages
        } catch (error) {
            
        }
    }

    onMount(()=> {
        listarCnaes()
    })

    function proximaPagina(){
        if(page + 1 >= totalPages) return;
        page = page + 1;
        listarCnaes();
    }

    function paginaAnterior(){
        if(page === 0) return;
        page = page - 1
        listarCnaes()
    }

    function irParaPagina(p: number){
        if(p < 0 || p >= totalPages) return;
        page = p;
        listarCnaes();
    }

    function formatarCNAE(termo: string){
        let digitos = termo.replace(/\D/g, "").slice(0,7)


        const p1 = digitos.substring(0,4)
        const p2 = digitos.substring(4,5)
        const p3 = digitos.substring(5,7)


        if(digitos.length <= 4){
            return p1;
        }

        if(digitos.length === 5){
            return `${p1} - ${p2}`
        }

        return `${p1}-${p2}/${p3}`
        
    }
</script>


<Content>
        <section class="p-4">
        <form action="" class="bg-white rounded-lg  p-4 " onsubmit={cadastrarCNAE}>
            <div class="grid g grid-cols-3 gap-4  w-full">
                <div class=" flex flex-col">
                    <label for="">Código <span class="text-red-500">*</span></label>
                    <input type="text" placeholder="Código de Classificação de Atividade Econômica " bind:value={codigo} oninput={
                        (e) => {
                            const input = e.currentTarget as HTMLInputElement
                            const valorDigitado = input.value
                            codigo = formatarCNAE(valorDigitado)
                        
                        } 
                    }>
                </div>
                <div class=" flex flex-col">
                    <label for="">Descrição  <span class="text-red-500">*</span></label>
                    <input type="text" placeholder="Descrição de Atividade Econômica" bind:value={descricao}>
                </div>
                <div class="flex flex-col mt-6">
                    <button type="submit" class="bg-green-600 hover:bg-green-800 text-white cursor-pointer rounded-lg p-2">Cadastrar</button>
                </div>
            </div>
        </form>
    </section>

    <section class="p-4">
        <div class="bg-white rounded-lg">
            <div class="grid grid-cols-">
                <input class="m-4 " type="text" placeholder="Digite o código ou a descrição que deseja para o CNAE" bind:value={termo} oninput={
                    (e) => {
                        const input = e.currentTarget as HTMLInputElement
                        const valorDigitado = input.value
                        termo = valorDigitado
                        listarCnaes()
                    }
                }>
            </div>
            <div class="grid grid-cols-1 ml-5 mr-5  border-gray-500 shadow-2xs border rounded-lg">
                {#each cnae as cnaes}
                <div class="p-1 border-t rounded ">
                    <span><span class="text-[#255331] font-bold m-2">Código: </span> {cnaes.codigo}</span> - <span class="font-bold m-3 text-[#255331]"> Descrição:</span><span> {cnaes.descricao}</span>
                </div>
                {/each}
            </div>

            <div class="text-center">
                {#each pagination as p }

                {#if p === "..."}
                    <span>...</span>
                {:else}
                     <button
                        onclick={() => irParaPagina(p)}
                        class:bg-green-600={p === page}
                        class:text-white={p === page}
                        class="px-3 py-1 border rounded cursor-pointer"
                    >
                     {p + 1}
                    </button>
                {/if}
                {/each}
                <button onclick={proximaPagina} disabled={page + 1 >= totalPages} class="cursor-pointer"> {">"} </button>
            </div>
        </div>
    </section>
</Content>