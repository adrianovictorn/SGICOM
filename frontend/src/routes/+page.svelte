<script>
	import brasao from "$lib/images/brasao.png";

</script>


<svelte:head>
    <title>SGICOM</title>
</svelte:head>


<section class="grid grid-rows-[0.2fr_2fr_0.1fr] min-h-screen ">
	<header class="bg-gray-900 grid grid-cols-3">
		<div></div>
		<div class="flex justify-center text-4xl text-center items-center">
			<h1 class="text-[#0F2037] bg-gradient-to-r  from-[#0F2037] to-[#fef603] font-bold text-center "><span class="text-white font-extralight bg-[#0F2037]">SGI </span>COM</h1>
		</div>

		<div class="flex text-white justify-end items-center mr-3 text-semibold ">
			<a href="/login" class="hover:underline cursor-pointer">Acesso Restrito</a>
		</div>
	</header>
	<main class="border bg-gray-200">
		<div class="bg-white rounded-2xl flex flex-col justify-center items-center h-[70vh] mt-5  ml-auto mr-auto  gap-4 w-[50vw] p-10 text-center font-semibold text-xl">
			<img src={brasao} alt="" class="bg-gray-900 rounded-lg shadow-2xl border w-60 border-white">
			<p>O SGICOM (Sistema de Gestão Industrial, Comercial e Municipal) é um software desenvolvido em Conceição do Almeida com o objetivo de fornecer dados precisos e uma base sólida de informações para apoiar a gestão municipal.</p>		
			<div class="mt-5 bg-">
				<button class="rounded-lg p-3 bg-[#d3d013]  shadow-2xl  text-white cursor-pointer">
					<a href="/login">Faça seu Login</a></button>
			</div> 
		
		</div>	
	</main>
	<footer class=" bg-gray-500 text-center text-white">Desenvolvido por Adriano Victor N. Ribeiro</footer>
</section>