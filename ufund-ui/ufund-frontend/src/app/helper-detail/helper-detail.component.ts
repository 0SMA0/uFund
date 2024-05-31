import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NeedService } from '../need.service';
import { Location } from '@angular/common';
import { Need } from '../need';

@Component({
  selector: 'app-helper-detail',
  templateUrl: './helper-detail.component.html',
  styleUrls: ['./helper-detail.component.css']
})
export class HelperDetailComponent {
  @Input() need?: Need;
  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location: Location
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
