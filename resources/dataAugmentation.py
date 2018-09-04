import Augmentor
import sys

def executePipeline(p, n):
	p.rotate90(probability=0.5)
	p.rotate270(probability=0.5)
	p.flip_left_right(probability=0.8)
	p.flip_top_bottom(probability=0.3)
	p.crop_random(probability=1, percentage_area=0.5)
	p.resize(probability=1.0, width=120, height=120)

	p.sample(n)

n = int(sys.argv[1])

if (sys.argv[2] == "test"):
	pBruegel = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/bruegelTest1",
							output_directory="/home/rafael/DataOnderzoeksproject2/test")
	executePipeline(pBruegel, n)

	pBruegel = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/bruegelTest2",
							output_directory="/home/rafael/DataOnderzoeksproject2/test")
	executePipeline(pBruegel, n)

	pMondriaan = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/mondriaanTest",
							output_directory="/home/rafael/DataOnderzoeksproject2/test")
	executePipeline(pMondriaan, n)

	pPicasso = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/picassoTest",
							output_directory="/home/rafael/DataOnderzoeksproject2/test")
	executePipeline(pPicasso, n)

	pRubens = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/rubensTest",
							output_directory="/home/rafael/DataOnderzoeksproject2/test")
	executePipeline(pRubens, n)
else:
	pBruegel = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/bruegel",
							output_directory="/home/rafael/DataOnderzoeksproject2/bruegel")
	executePipeline(pBruegel, n)

	pMondriaan = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/mondriaan",
							output_directory="/home/rafael/DataOnderzoeksproject2/mondriaan")
	executePipeline(pMondriaan, n)

	pPicasso = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/picasso",
							output_directory="/home/rafael/DataOnderzoeksproject2/picasso")
	executePipeline(pPicasso, n)

	pRubens = Augmentor.Pipeline("/home/rafael/Dropbox/MasterInformatica/TweedeJaar/Onderzoeksproject2/GraphicsParser/src/main/resources/img/rubens",
							output_directory="/home/rafael/DataOnderzoeksproject2/rubens")
	executePipeline(pRubens, n)