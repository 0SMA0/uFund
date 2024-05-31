import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent{
  @Input() need?: Need;
  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location:  Location
  ) {}
  ngOnInit(): void {
    this.getNeed();
  }
  getNeed(): void {
    const name = String(this.route.snapshot.paramMap.get('name'));
    this.needService.getNeed(name).subscribe(need => this.need = need);
  }
  goBack(): void {
    this.location.back();
  }
  save(): void{
    if(this.need) {
      this.needService.updateNeed(this.need).subscribe(() => this.goBack());
    }
  }
}